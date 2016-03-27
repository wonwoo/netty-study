package me.wonwoo.chat;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by wonwoo on 2016. 3. 27..
 */
@Data
@AllArgsConstructor
public class Message {
  private Herder herder;
  private String text;

}
