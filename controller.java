import java.util.*;
import java.io.*;
import java.lang.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.*;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JPanel;


public class controller{
  private String command = "end";
  private JPanel buttonPanel = new JPanel();;
  private JButton act;
  private JButton move;
  private JButton work;
  private JButton rehearse;
  private JButton upgrade;
  private JButton end;
  //private static Board display = new Board();

  public static void main(String[] args) throws Exception{
    controller c = new controller();
    c.start(args);
  }

  private void start(String[] args) throws Exception{
    Board display = new Board();
    JFrame frame = new JFrame();


    Deadwood2 deadwood = new Deadwood2();

    makeButtons();
    int numPlayers = Integer.parseInt(args[0]);
    int totalScenes = 22;
    int remainingScenes = totalScenes;

    display.makeBoard(); //Board LayeredPane
    display.setUpPlayerInfo(); //Player LayeredPane
    frame.add(buttonPanel);
    buttonPanel.setBounds(1201, 500, 90, 400);
    display.makeDice(numPlayers);

    frame.setTitle("Deadwood");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setPreferredSize(new Dimension(1400,900)); //not right dimension
    frame.setResizable(false);
    //frame.add(buttonPanel, BoardLayout.EAST);
    frame.getContentPane().add(display);
    frame.pack();
    frame.setVisible(true);


    int numDays = deadwood.setDays(numPlayers);
    ArrayList<Player> players = deadwood.makePlayers(numPlayers);
    deadwood.board();
    deadwood.cards();
    deadwood.makeGame(display);

    String room = "";
    while(numDays > 0){
      deadwood.associateCards(display);
      while(remainingScenes > totalScenes - 1){
        for(Player x : players){
          if(command.compareToIgnoreCase("end") != 0){
            if(remainingScenes > totalScenes - 1){

              String color = x.getPlayerColor();
              int dollars = x.getPlayerDollars();
              int credits = x.getPlayerCredits();
              int rehearsalCredits = x.getPlayerRehearsalCredits();
              display.addPlayerInfo(color, credits, dollars, rehearsalCredits);
              if(command.compareToIgnoreCase("act") == 0){
                deadwood.act(x);
              }else if(command.compareToIgnoreCase("move") == 0){
                //deadwood.move(x);
              }
              //deadwood.act(x);
              //deadwood.
              /////////////////////////////////////turns
              room = x.getPlayerLocation();
            }
          }
        }
      }
      deadwood.resetGame(room);
      remainingScenes = totalScenes;
      numDays--;
    }

    for(Player x : players){
      deadwood.finalScore(x);
    }

  }

  public void callAct(){
    command = "act";
  }

  public void callMove(){
   command = "move";
  }

  private void makeButtons(){
    buttonPanel.setSize(200, 500);
    act = new JButton("Act");
    act.addMouseListener(new CustomMouseListener("act"));
    act.setBackground(Color.green);
    act.setBounds(1210, 300, 60, 30);

    move = new JButton("Move");
    move.addMouseListener(new CustomMouseListener("move"));
    move.setBackground(Color.gray);
    move.setBounds(1210, 350, 75, 30);

    work = new JButton("Work");
    work.setBackground(Color.red);
    work.setBounds(1210, 400, 75, 30);

    rehearse = new JButton("Rehearse");
    rehearse.setBackground(Color.pink);
    rehearse.setBounds(1210, 450, 110, 30);

    upgrade = new JButton("Upgrade");
    upgrade.setBackground(Color.blue);
    upgrade.setBounds(1210, 500, 100, 30);

    end = new JButton("End Turn");
    end.setBackground(Color.cyan);
    end.setBounds(1210, 550, 100, 30);

    buttonPanel.add(act);
    buttonPanel.add(move);
    buttonPanel.add(work);
    buttonPanel.add(rehearse);
    buttonPanel.add(upgrade);
    buttonPanel.add(end);
  }

  class CustomMouseListener implements MouseListener {
     private String button;
     public CustomMouseListener(String name){
      button = name;
     }
     public void mouseClicked(MouseEvent e) {
          switch(button){
          case "act":
          callAct();
          System.out.println("CLICKED ACT");
          break;
          case "move":
          callMove();
          System.out.println("CLICKED MOVE");
          break;
          default:

          System.out.println("ERROR NO BUTTON");
        }

     }

     public void mousePressed(MouseEvent e) {
     }
     public void mouseReleased(MouseEvent e) {
     }
     public void mouseEntered(MouseEvent e) {
     }
     public void mouseExited(MouseEvent e) {
     }
  }

}
