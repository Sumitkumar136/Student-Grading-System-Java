import javax.swing.*;

// import temp.Dashboard;

import java.awt.*;
import java.awt.event.*;

public class LoginPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginBtn;

    public LoginPage() {
        setTitle("Login - Student Grade Tracker");
        setSize(450, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main Panel with Gradient Background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(135, 206, 235),
                                                     0, getHeight(), new Color(25, 25, 112));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("Student Grade Tracker", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        // Card Panel (White Box for Login Form)
        JPanel cardPanel = new JPanel(new GridLayout(3, 2, 10, 15));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));
        cardPanel.setBackground(Color.WHITE);

        // Labels with Icons
        JLabel userLabel = new JLabel(" Username:");
        userLabel.setIcon(new ImageIcon("user.png")); // optional icon if available
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel passLabel = new JLabel(" Password:");
        passLabel.setIcon(new ImageIcon("lock.png")); // optional icon if available
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Input Fields
        usernameField = new JTextField();
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180), 1, true),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)));

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180), 1, true),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)));

        // Button
        loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(70, 130, 180));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginBtn.setFocusPainted(false);
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginBtn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Hover Effect
        loginBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginBtn.setBackground(new Color(30, 144, 255));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginBtn.setBackground(new Color(70, 130, 180));
            }
        });

        // Add components
        cardPanel.add(userLabel);
        cardPanel.add(usernameField);
        cardPanel.add(passLabel);
        cardPanel.add(passwordField);
        cardPanel.add(new JLabel(""));
        cardPanel.add(loginBtn);

        // Footer
        JLabel footer = new JLabel("Developed by Sumit Kumar", JLabel.CENTER);
        footer.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        footer.setForeground(Color.LIGHT_GRAY);
        footer.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Add all parts
        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(cardPanel, BorderLayout.CENTER);
        mainPanel.add(footer, BorderLayout.SOUTH);

        add(mainPanel);

        // Action Listener
        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginAction();
            }
        });

        // Enter key press also triggers login
        passwordField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginAction();
            }
        });

        getRootPane().setDefaultButton(loginBtn);

        setVisible(true);
    }

    private void loginAction() {
        String user = usernameField.getText();
        String pass = new String(passwordField.getPassword());

        if (user.equals("admin") && pass.equals("1234")) {
            JOptionPane.showMessageDialog(null, "Login Successful!");
            dispose();
            new Dashboard();
        } else {
            JOptionPane.showMessageDialog(null, "Invalid Credentials!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}
