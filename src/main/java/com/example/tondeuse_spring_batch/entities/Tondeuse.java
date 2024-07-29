package com.example.tondeuse_spring_batch.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Tondeuse {
 @Id
 private Long id;
 private int x;
 private int y;
 private char orientation;

 public Tondeuse() {
 }

 public Tondeuse(Long id) {
  this.id = id;
 }


 public Tondeuse(int x, int y, char orientation) {
  this.x = x;
  this.y = y;
  this.orientation = orientation;
 }

 public int getX() {
  return x;
 }

 public void setX(int x) {
  this.x = x;
 }

 public int getY() {
  return y;
 }

 public void setY(int y) {
  this.y = y;
 }

 public char getOrientation() {
  return orientation;
 }

 public void setOrientation(char orientation) {
  this.orientation = orientation;
 }

 @Override
 public String toString() {
  return x + " " + y + " " + orientation;
 }
}

