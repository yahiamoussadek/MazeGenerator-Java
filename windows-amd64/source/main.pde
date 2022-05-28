int cols;
int rows;
int size = 40;
boolean isFinished = false;
Block[][] block;
Block CurrentMazeBlock;

ArrayList<Block> stack = new ArrayList<Block>();

void setup() {
  size(480, 480);
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
  
  frameRate(10);
}

void draw(){
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

void removeWalls(Block current, Block next) {
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
