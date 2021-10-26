package ydrive.app_ydrive.service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import ydrive.app_ydrive.model.Fruit;
import ydrive.app_ydrive.model.FruitMapper;

@Service
public class AsyncShopService57 {
  boolean dbUpdated = false;

  private final Logger logger = LoggerFactory.getLogger(AsyncShopService57.class);

  @Autowired
  FruitMapper fMapper;

  public ArrayList<Fruit> syncShowFruitsList() {
    return fMapper.selectAllFruit();
  }

  @Transactional
  public Fruit syncBuyFruits(int id) {
    Fruit fruit = fMapper.selectById(id);
    fMapper.deleteById(id);
    this.dbUpdated = true;

    return fruit;
  }

  @Async
  public void asyncShowFruitsList(SseEmitter emitter) {
    dbUpdated = true;
    try {
      while (true) {
        if (false == dbUpdated) {
          TimeUnit.MILLISECONDS.sleep(500);
          continue;
        }
        ArrayList<Fruit> fruits7 = this.syncShowFruitsList();
        emitter.send(fruits7);
        TimeUnit.MILLISECONDS.sleep(1000);
        dbUpdated = false;
      }
    } catch (Exception e) {
      logger.warn("Exception:" + e.getClass().getName() + ":" + e.getMessage());
    } finally {
      emitter.complete();
    }
    System.out.println("asyncShowFruitsList complete");
  }

}
