import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class MyGame extends JFrame implements ActionListener {

    JLabel heading, clockLabel;
    JPanel mainPanel;
    Font font = new Font("", Font.BOLD, 40);

    JButton[] buttons = new JButton[9];


    /// Game instance Variables ////
    int gameChances[] = {3,3,3,3,3,3,3,3,3};
    int activePlayer = 0;

    Boolean gameOver = false;

    int wps[][] = {
            {0,1,2},
            {3,4,5},
            {6,7,8},
            {0,3,6},
            {1,4,7},
            {2,5,8},
            {0,4,8},
            {2,4,6}
    };

    int winner;

    public MyGame() {
        System.out.println("Creating Instance of MyGame class");
        setTitle("Tic-Tac-Toe Game");
        setSize(1050, 850);
        ImageIcon imageIcon = new ImageIcon("src/image/Tic-Tac-Toe-Game.png");
        setIconImage(imageIcon.getImage());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createGUI();
        setVisible(true);
    }

    private void createGUI(){
        this.getContentPane().setBackground(Color.decode("#2196f3"));
        this.setLayout(new BorderLayout());
        // Creating Heading
        heading = new JLabel("Tic-Tac-Toe");
        heading.setForeground(Color.WHITE);
        heading.setFont(font);
        heading.setHorizontalAlignment(SwingConstants.CENTER);

        this.add(heading, BorderLayout.NORTH);

        //Creating ClockLabel for bottom
        clockLabel = new JLabel("Clock");
        clockLabel.setForeground(Color.WHITE);
        clockLabel.setFont(font);
        clockLabel.setHorizontalAlignment(SwingConstants.CENTER);

        this.add(clockLabel, BorderLayout.SOUTH);

        Thread clockThread = new Thread(){
            @Override
            public void run() {
                try {
                    while(true){

                        String dateTime = new Date().toLocaleString();
                        clockLabel.setText(dateTime);

                       Thread.sleep(1000);

               }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        };
        clockThread.start();


        /// Panel Section /////
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3,3));

        for (int i=1; i<=9; i++){
            JButton button = new JButton();
            button.setBackground(Color.BLACK);
            button.setFont(font);
            mainPanel.add(button);
            buttons[i-1] = button;

            button.addActionListener(this);
            button.setName(String.valueOf(i-1));
        }
        this.add(mainPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton currentButton  = (JButton) e.getSource();
        String nameStr = currentButton.getName();
        int name = Integer.parseInt(nameStr.trim());

        if (gameOver){
            JOptionPane.showMessageDialog(this, "Game is Already Over !!! ");
            return;
        }

        if(gameChances[name] == 3){
            if(activePlayer == 0){
                currentButton.setText("0");
                gameChances[name] = activePlayer;
                activePlayer = 1;
            }else{
                currentButton.setText("X");
                gameChances[name] = activePlayer;
                activePlayer = 0;
            }

            /// Find the Winner /////
            for(int []temp: wps){
                if(gameChances[temp[0]]==gameChances[temp[1]] && gameChances[temp[1]]==gameChances[temp[2]] && gameChances[temp[2]] != 3){
                    winner = gameChances[temp[0]];
                    gameOver = true;
                    JOptionPane.showMessageDialog(null, "Player " + winner + " has won the game !!!");
                    int i= JOptionPane.showConfirmDialog(this, "Would you like to play Again ?");
                    if(i==0){
                        this.setVisible(false);
                        new MyGame();
                    } else if(i==1){
                        System.exit(0);
                    }else{

                    }
                    break;
                }
            }

            //// Draw Logic ////
            int chances = 0;

            for (int x : gameChances){
                if (x==3){
                    chances++;
                    break;

                }
            }
            if (chances==0 && gameOver == false){
                JOptionPane.showMessageDialog(null, "It's a Draw...");
                int i= JOptionPane.showConfirmDialog(this, "Would you like to play Again ?");
                if(i==0){
                    this.setVisible(false);
                    new MyGame();
                } else if(i==1){
                    System.exit(0);
                }else{

                }

                gameOver = true;

            }
        }
        else{
            JOptionPane.showMessageDialog(this,"This position is already filled...." +
                    "\n Try any other place...");
        }
    }

}
