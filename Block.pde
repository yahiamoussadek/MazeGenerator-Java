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
  void show() {
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
  
  void addNeighbours() {
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

  boolean hasUnvisited() {
    for (Block neighbour : neighbours){
      if (!neighbour.visited) {
        return true;
      }    
    }
    return false;
  }
  
  Block randomNeighbour() {
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
