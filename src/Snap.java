package src;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Snap extends JFrame implements ActionListener {

    private int width;
    private int height;

    private Player[] players = {new Player(), new Player()};

    private Player player;
    private JLabel playerCount;
    private JButton playerHand;

    private Player AI;
    private JLabel AICount;
    private Timer AISnapTimer;
    private Timer AIMoveTimer;

    private Deck deck;
    private JLabel deckCount;
    private JLabel topCard;
    private JButton snapButton;
    
    Snap(){
        this.setTitle("Snap! - Are you fast enough to beat a robot?");
        this.setIconImage(new ImageIcon("src/assets/Joker.png").getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(800,500));
        this.setSize(getPreferredSize());
        this.setResizable(false);
        this.setVisible(true);

        this.getContentPane().setBackground(new Color(0,100,0));
        this.setOpacity(1.0f);

        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        this.setLocationRelativeTo(null);

        width = this.getContentPane().getWidth()/3;
        height = this.getContentPane().getHeight();

        Font fontHeavy = new Font("Times New Roman", Font.BOLD, 36);
        Font fontNormal = new Font("Times New Roman", Font.PLAIN, 20);

        Dimension panelSize = new Dimension(width, height);
        Color darkerGreen = new Color(0,80,0);

        ImageIcon cardBack = new ImageIcon("src/assets/cardback.png");
        ImageIcon cardResized = new ImageIcon(cardBack.getImage().getScaledInstance(width-40, height-200, Image.SCALE_DEFAULT));
        

        // ==PLAYER INFO==
        JPanel playerPanel = new JPanel();
        playerPanel.setPreferredSize(panelSize);
        playerPanel.setSize(playerPanel.getPreferredSize());
        playerPanel.setBackground(darkerGreen);
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));

        JLabel playerName = new JLabel("You");
        playerName.setFont(fontHeavy);
        playerName.setForeground(Color.WHITE);
        playerName.setAlignmentX(Component.CENTER_ALIGNMENT);

        playerHand = new JButton(cardResized);
        playerHand.addActionListener(this);
        playerHand.setBorderPainted(false);
        playerHand.setFocusPainted(false);
        playerHand.setContentAreaFilled(false);
        playerHand.setAlignmentX(Component.CENTER_ALIGNMENT);

        playerCount = new JLabel();
        playerCount.setFont(fontNormal);
        playerCount.setForeground(Color.WHITE);
        playerCount.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        playerPanel.add(Box.createVerticalGlue());
        playerPanel.add(playerName);
        playerPanel.add(Box.createVerticalGlue());
        playerPanel.add(playerHand);
        playerPanel.add(Box.createVerticalGlue());
        playerPanel.add(playerCount);
        playerPanel.add(Box.createVerticalGlue());


        // ==Deck INFO==
        JPanel deckPanel = new JPanel();
        deckPanel.setPreferredSize(panelSize);
        deckPanel.setSize(playerPanel.getPreferredSize());
        deckPanel.setOpaque(false);
        deckPanel.setLayout(new BoxLayout(deckPanel, BoxLayout.Y_AXIS));

        deckCount = new JLabel();
        deckCount.setFont(fontNormal);
        deckCount.setForeground(Color.WHITE);
        deckCount.setAlignmentX(Component.CENTER_ALIGNMENT);

        topCard = new JLabel();
        topCard.setAlignmentX(Component.CENTER_ALIGNMENT);

        snapButton = new JButton("SNAP!");
        snapButton.addActionListener(this);
        snapButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        deckPanel.add(Box.createVerticalGlue());
        deckPanel.add(deckCount);
        deckPanel.add(Box.createVerticalGlue());
        deckPanel.add(topCard);
        deckPanel.add(Box.createVerticalGlue());
        deckPanel.add(snapButton);
        deckPanel.add(Box.createVerticalGlue());


        // ==AI INFO==
        JPanel AIPanel = new JPanel();
        AIPanel.setPreferredSize(panelSize);
        AIPanel.setSize(AIPanel.getPreferredSize());
        AIPanel.setBackground(darkerGreen);
        AIPanel.setLayout(new BoxLayout(AIPanel, BoxLayout.Y_AXIS));

        JLabel AIName = new JLabel("Computer");
        AIName.setFont(fontHeavy);
        AIName.setForeground(Color.WHITE);
        AIName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel AIHand = new JLabel(cardResized);
        AIHand.setAlignmentX(Component.CENTER_ALIGNMENT);

        AICount = new JLabel();
        AICount.setFont(fontNormal);
        AICount.setForeground(Color.WHITE);
        AICount.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        AIPanel.add(Box.createVerticalGlue());
        AIPanel.add(AIName);
        AIPanel.add(Box.createVerticalGlue());
        AIPanel.add(AIHand);
        AIPanel.add(Box.createVerticalGlue());
        AIPanel.add(AICount);
        AIPanel.add(Box.createVerticalGlue());


        this.add(playerPanel);
        this.add(deckPanel);
        this.add(AIPanel);

        this.repaint();
        this.pack();

        InitialiseGame();

    }

    public void InitialiseGame(){
        deck = new Deck(); // includes autoshuffle

        player = players[0];
        AI = players[1];

        deck.deal(players);
        player.displayDeck();
        
        AISnapTimer = new Timer((int)Math.floor(Math.random()* (1000 - 500 + 1) + 500 ), this);
        AIMoveTimer = new Timer(900, this);

        refreshPlayer();
        refreshAI();
        refreshDeck();
    }

    public void refreshPlayer(){
        playerCount.setText("Cards: " + player.getDeckSize());
    }

    public void refreshAI(){
        AICount.setText("Cards: " + AI.getDeckSize());
    }

    public void refreshDeck(){
        deckCount.setText("Cards: " + deck.getDeckSize());

        if(deck.getDeckSize() > 0){
            ImageIcon newCard = new ImageIcon(new ImageIcon("src/assets/" + deck.getCards().get(deck.getDeckSize()-1).getCardName() + ".png").getImage());
            topCard.setIcon(newCard);
        }
        
    }

    @Override
    public void actionPerformed(ActionEvent e){
    
        if (e.getSource() == playerHand){
            deck.addCard(player.place());
            playerHand.setEnabled(false);


            if(AISnapTimer.isRunning()){
                    AISnapTimer.stop();
                }

            if(AIMoveTimer.isRunning()){
                AIMoveTimer.stop();
            }

            if(player.getDeckSize() == 0){

            JOptionPane.showMessageDialog(this, "Congratulations \n You beat the robot!");
            this.dispose();

            }else{

                if (AICheckSnap()){
                
                AISnapTimer.setDelay((int)Math.floor(Math.random()* (1000 - 500 + 1) + 500));
                AISnapTimer.start();

                }else{
                    
                    AIMoveTimer.start();
                }

            }

            
        }
        else if(e.getSource() == snapButton){
            
            if(AISnapTimer.isRunning()){
                
                AISnapTimer.stop();
            }

            if(deck.getCards().get(deck.getCards().size()-1).getNumber() == deck.getCards().get(deck.getCards().size()-2).getNumber()){
                AI.addCards(deck.getCards());
                deck.clearDeck();

                playerHand.setEnabled(true);

            }else{
                player.addCards(deck.getCards());
                deck.clearDeck();   

                playerHand.setEnabled(false);
                AIMoveTimer.start();

            } 

        }else if(e.getSource() == AISnapTimer){

            AISnapTimer.stop();
            playerHand.setEnabled(false);

            player.addCards(deck.getCards());
            deck.clearDeck();  

            System.out.println("AI SNAPPED");
            
            AIMoveTimer.start();

        
        }else if(e.getSource() == AIMoveTimer){
            deck.addCard(AI.place());
            playerHand.setEnabled(true);

            if (AI.getDeckSize() == 0){
       
            JOptionPane.showMessageDialog(this, "Oh No! \n The robot was faster all along... ");
            this.dispose();
            
            }else{
                if(AIMoveTimer.isRunning()){
                AIMoveTimer.stop();
                }

                if (AICheckSnap()){
                    
                    AISnapTimer.setDelay((int)Math.floor(Math.random()* (1000 - 500 + 1) + 500));
                    AISnapTimer.start();

                }
            }

            
        
        }

        refreshPlayer();
        refreshDeck();
        refreshAI();
    }

    public boolean AICheckSnap(){
        if (deck.getDeckSize() < 2){
            return false;
        }

        if (deck.getCards().get(deck.getDeckSize()-1).getNumber()== deck.getCards().get(deck.getDeckSize()-2).getNumber()){
            return true;
        }

        return false;

    }

    public static void main(String[] args) {
        new Snap();

    }

}
