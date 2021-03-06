/* autogenerated by Processing revision 1283 on 2022-05-28 */
import processing.core.*;
import processing.data.*;
import processing.event.*;
import processing.opengl.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

public class main extends PApplet {

int cols;
int rows;
int size = 40;
boolean isFinished = false;
Block[][] block;
Block CurrentMazeBlock;

ArrayList<Block> stack = new ArrayList<Block>();

 public void setup() {
  /* size commented out by preprocessor */;
  rows = height/size;
  cols = width/size;
  block =  new Block[rows][cols];
  for (int i = 0; i < rows; i++){
    for (int j = 0; j < cols; j++){
      block[i][j] = new Block(i, j);
    }
  }
  
  for (int i = 0 ; i < rows; i++){
    for (int j = 0;  j< cols; j++){
      block[i][j].addNeighbours();
    }
  }
   
  CurrentMazeBlock = block[0][0];
  CurrentMazeBlock.visited = true;
  
  frameRate(25);
}

 public void draw(){
  if (!isFinished){
    background(128, 206, 214);
    strokeWeight(2);
    for (int i = 0; i < rows; i++){
      for (int j = 0 ; j < cols; j++){
        block[i][j].show();
      }
    }
    fill(214, 65, 97);
    rect(CurrentMazeBlock.x, CurrentMazeBlock.y, size, size);
    
    if (CurrentMazeBlock.hasUnvisited()){
      Block nextCurrent = CurrentMazeBlock.randomNeighbour();
      stack.add(CurrentMazeBlock);
      removeWalls(CurrentMazeBlock, nextCurrent);
      CurrentMazeBlock = nextCurrent;
    }
    else if(stack.size() > 0){
      Block nextCurrent = stack.get(stack.size()-1);
      stack.remove(nextCurrent);
      CurrentMazeBlock = nextCurrent;
    }
    else {
      print("Maze Done");
      noLoop();
    }
  }
}

 public void removeWalls(Block current, Block next) {
  int xDistance = current.thisRow - next.thisRow;
  int yDistance = current.thisCol - next.thisCol;
  
  if (xDistance == -1) {
    current.walls[1] = false;
    next.walls[3] = false;
  }
  else if (xDistance == 1){
    current.walls[3] = false;
    next.walls[1] = false;
  }
  else if (yDistance == 1) {
    current.walls[0] = false;
    next.walls[2] = false;
  }
  else if (yDistance == -1){
    current.walls[2] = false;
    next.walls[0] = false;
  }
}
class Block {
  int x, y, thisRow, thisCol;
  boolean visited = false;
  boolean[] walls = {true, true, true, true};
  ArrayList<Block> neighbours = new ArrayList<Block>();
  
  
  Block(int row, int col){
     x = row * size;
     y = col * size;
     thisRow = row;
     thisCol = col;
  }
   public void show() {
    if (walls[0]){
      line(x, y, x+size, y);
    }
    if (walls[1]) {
      line(x + size, y, x+size, y+size);
    }
    if (walls[2]) {
      line(x + size, y + size, x, y+ size);
    }
    if (walls[3]){
      line(x, y+size, x, y);
    }
    if (visited) {
      noStroke();
      fill(201, 76, 76, 95);
      rect(x, y, size, size);
      stroke(0);
    }
  }
  
   public void addNeighbours() {
    if (thisRow > 0){
      neighbours.add(block[thisRow -1][thisCol]);
    }
    if (thisCol < cols - 1){
      neighbours.add(block[thisRow][thisCol+1]);
    }
    if (thisRow < rows - 1){
      neighbours.add(block[thisRow+1][thisCol]);
    }
    if (thisCol > 0){
      neighbours.add(block[thisRow][thisCol -1 ]);
    }
  }

   public boolean hasUnvisited() {
    for (Block neighbour : neighbours){
      if (!neighbour.visited) {
        return true;
      }    
    }
    return false;
  }
  
   public Block randomNeighbour() {
    Block ngbr = neighbours.get(floor(random(0, neighbours.size())));
    while (ngbr.visited){
      neighbours.remove(ngbr);
      ngbr = neighbours.get(floor(random(0, neighbours.size())));
    }
    ngbr.visited = true;
    neighbours.remove(ngbr);
    return ngbr;
  }

}


  public void settings() { size(480, 480); }

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "main" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
