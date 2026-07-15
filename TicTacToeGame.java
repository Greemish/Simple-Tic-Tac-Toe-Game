package tictactoegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToeGame extends JFrame {
    private JButton[][] buttons = new JButton[3][3];
    private JButton startButton;
    private JLabel statusLabel;
    private boolean playerOneTurn = true;
    private boolean gameActive = false;

    public TicTacToeGame() {
        setTitle("Tic Tac Toe");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Board panel
        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton("");
                buttons[row][col].setFont(new Font("Arial", Font.BOLD, 60));
                buttons[row][col].addActionListener(new CellClickListener(row, col));
                boardPanel.add(buttons[row][col]);
            }
        }

        // Control panel
        JPanel controlPanel = new JPanel(new BorderLayout());
        startButton = new JButton("Start Game");
        statusLabel = new JLabel("Click Start to begin", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        startButton.addActionListener(e -> resetBoard());

        controlPanel.add(startButton, BorderLayout.WEST);
        controlPanel.add(statusLabel, BorderLayout.CENTER);

        add(boardPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Inner class for handling cell clicks
    private class CellClickListener implements ActionListener {
        private int row, col;

        public CellClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!gameActive) return; // ignore clicks before start
            if (buttons[row][col].getText().equals("")) {
                if (playerOneTurn) {
                    buttons[row][col].setText("X");
                    if (checkWinner("X")) {
                        statusLabel.setText("Player 1 wins!");
                        JOptionPane.showMessageDialog(null, "Player 1 wins!");
                        resetBoard();
                        gameActive = false;
                        return;
                    }
                    statusLabel.setText("Player 2's turn");
                } else {
                    buttons[row][col].setText("O");
                    if (checkWinner("O")) {
                        statusLabel.setText("Player 2 wins!");
                        JOptionPane.showMessageDialog(null, "Player 2 wins!");
                        resetBoard();
                        gameActive = false;
                        return;
                    }
                    statusLabel.setText("Player 1's turn");
                }
                playerOneTurn = !playerOneTurn;

                // Check for draw
                if (isBoardFull() && gameActive) {
                    statusLabel.setText("It's a draw!");
                    gameActive = false;
                }
            }
        }
    }

    // Reset method
    private void resetBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");
            }
        }
        playerOneTurn = true;
        gameActive = true;
        statusLabel.setText("Player 1's turn");
    }

    // Check winner
    private boolean checkWinner(String symbol) {
        // Rows
        for (int row = 0; row < 3; row++) {
            if (buttons[row][0].getText().equals(symbol) &&
                buttons[row][1].getText().equals(symbol) &&
                buttons[row][2].getText().equals(symbol)) {
                return true;
            }
        }
        // Columns
        for (int col = 0; col < 3; col++) {
            if (buttons[0][col].getText().equals(symbol) &&
                buttons[1][col].getText().equals(symbol) &&
                buttons[2][col].getText().equals(symbol)) {
                return true;
            }
        }
        // Diagonals
        if (buttons[0][0].getText().equals(symbol) &&
            buttons[1][1].getText().equals(symbol) &&
            buttons[2][2].getText().equals(symbol)) {
            return true;
        }
        if (buttons[0][2].getText().equals(symbol) &&
            buttons[1][1].getText().equals(symbol) &&
            buttons[2][0].getText().equals(symbol)) {
            return true;
        }
        return false;
    }

    // Check if board is full
    private boolean isBoardFull() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col].getText().equals("")) {
                    return false;
                }
            }
        }
        JOptionPane.showMessageDialog(null, "It's a draw!");
        return true;
    }

    public static void main(String[] args) {
        new TicTacToeGame();
    }
}
