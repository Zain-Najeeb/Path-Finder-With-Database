

import java.awt.geom.Point2D;
import processing.core.PApplet;

import java.util.*;

public class Maze extends PApplet {
    public static int score = 0;
    public static int highscore = 0 ;


    public static database getdata = new database();
    public static player player = new player();
    public static Maze maze = new Maze();
    boolean start = true;
    public menus view = new mainmenu();
    public  static MazeGenerator mazeGenerator = new MazeGenerator();
    public static Point2D end ;

    public static List<Point2D> wallsPositions = new ArrayList<>();
    public static Maze instance;
    public static HashMap<Point2D, Node> playervalid = new HashMap<>();
    public static HashMap<Point2D, Node> valid = new HashMap<>();
    public void settings() {
        if (instance == null) {
            instance = this;
        }
        size(1200, 800);


    }
    public void draw() {
        if (start) {
            instance.ellipseMode(CENTER);

            start = false;
         maze.view.view();
        }
        if (maze.view.getMenu().equalsIgnoreCase("main")) {
            gameView.circle();
            gameView.highscore();

        } else if (maze.view.getMenu().equalsIgnoreCase("menu")) {
                mainmenu.text();
        }


    }
    public void mousePressed() {
        int x = mouseX;
        int y = mouseY;
        y = roundpos(y);
        x = roundpos(x);
        try {
            maze.view.actions(x,y);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void keyPressed() {
        if (maze.view.getMenu().equalsIgnoreCase("main")) {

            try {
                player.move(key);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (maze.view.getMenu().equalsIgnoreCase("menu")) {
            try {
                mainmenu.keyPressed(key);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public int roundpos(int cord) {
        int temp = cord%25;
        cord = cord - temp;
        return cord;
    }

    public void grid() {
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 32; j++) {
                valid.put(addCord(j * 25, i * 25), new Node(j * 25, i * 25));
                playervalid.put(addCord(j * 25, i * 25), new Node(j * 25, i * 25));
                valid.get(addCord(j * 25, i * 25)).distance = MAX_INT;
                Maze.instance.fill(255,255,255);
                Maze.instance.rect(j * 25, i * 25, 25, 25);
            }
        }

        Maze.instance.fill(0,255,0);
        Maze.instance.rect(0, 0, 25, 25);
    }

    public Point2D addCord(int xcord, int ycord) {
        return new Point2D.Double(xcord, ycord);

    }
      public void clearing() {

        valid.clear();
        gameView.fastest.clear();
        wallsPositions.clear();
        playervalid.clear();
        MazeGenerator.movesvalid.clear();
        player.x = 12.5f;
        player.y = 12.5f;
    }

    public void reset() {
        Maze.instance.fill(204 ,204, 204);
        Maze.instance.rect(0,0,1200,800);
    }

    public static HashMap<String, Integer> sortscore(HashMap<String, Integer> map ) {
        ArrayList<Integer> sort = new ArrayList<>(map.values());
        LinkedHashMap<String, Integer> newMap =  new LinkedHashMap<>() ;
        Collections.sort(sort);
        for (int i = sort.size()-1; i >= 0 ; i--) {
            for (String key : map.keySet()) {
                if (map.get(key).equals(sort.get(i))) {
                    newMap.put(key, sort.get(i));
                    map.remove(key);
                    break;
                }
            }
        }
        return newMap;
    }

}
