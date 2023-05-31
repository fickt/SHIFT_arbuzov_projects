package ru.cft.javaLessons.miner.listeners;

import java.util.function.Consumer;

@FunctionalInterface
public interface LoseGameEventListener {
      void onLose();
}
