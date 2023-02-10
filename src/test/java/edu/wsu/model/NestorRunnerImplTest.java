package edu.wsu.model;

import static org.junit.jupiter.api.Assertions.*;

class NestorRunnerImplTest {

  @org.junit.jupiter.api.Test
  void getWinner() {
    NestorRunner ttt = new NestorRunnerImpl();
    assertEquals("Bertie", ttt.getWinner());
  }
}