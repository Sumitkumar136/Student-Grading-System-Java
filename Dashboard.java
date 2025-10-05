import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.Border;

// import temp.RoundedButton;

public class Dashboard extends JFrame {

    private JPanel contentArea; // global bana diya taki button click par use kar saken

    public Dashboard() {
        setTitle("Dashboard - Student Grade Tracker");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main Panel with Gradient Background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(135, 206, 250),
                        getWidth(), getHeight(), new Color(25, 25, 112));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout());

        // ===== HEADER =====
        JLabel header = new JLabel("Student Grade Tracker - Dashboard", JLabel.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.setForeground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        mainPanel.add(header, BorderLayout.NORTH);

        // ===== SIDEBAR =====
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(6, 1, 0, 15));
        sidebar.setBackground(new Color(25, 25, 112));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        sidebar.setPreferredSize(new Dimension(200, getHeight()));  // thoda bada sidebar

        String[] menuItems = {"Add Student", "Enter Grades", "View Reports", "Statistics", "Settings", "Logout"};

        for (String item : menuItems) {
            JButton btn = new RoundedButton(item); // custom rounded button
            btn.setFocusPainted(false);
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            btn.setForeground(Color.WHITE);
            btn.setBackground(new Color(70, 130, 180));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Hover effect
            btn.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    btn.setBackground(new Color(30, 144, 255));
                }

                public void mouseExited(MouseEvent evt) {
                    btn.setBackground(new Color(70, 130, 180));
                }
            });

            // ===== BUTTON ACTIONS =====
            if (item.equals("Add Student")) {
                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        contentArea.removeAll();
                        contentArea.add(new AddStudentPanel(), BorderLayout.CENTER);
                        contentArea.revalidate();
                        contentArea.repaint();
                    }
                });
            } else if (item.equals("Enter Grades")) {
                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        contentArea.removeAll();
                        // contentArea.add(new JLabel("Enter Grades Section", JLabel.CENTER), BorderLayout.CENTER);
                        // contentArea.add(new AddStudentPanel(), BorderLayout.CENTER);
                        contentArea.add(new EnterGradePanel(), BorderLayout.CENTER);
                        contentArea.revalidate();
                        contentArea.repaint();
                    }
                });
            } else if (item.equals("View Reports")) {
                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        contentArea.removeAll();
                        // contentArea.add(new JLabel("Reports Section", JLabel.CENTER), BorderLayout.CENTER);
                        contentArea.add(new ViewReportPanel(), BorderLayout.CENTER);
                        contentArea.revalidate();
                        contentArea.repaint();
                    }
                });
            } else if (item.equals("Statistics")) {
                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        contentArea.removeAll();
                        // contentArea.add(new JLabel("Statistics Section", JLabel.CENTER), BorderLayout.CENTER);
                        contentArea.add(new StatisticsPanel(), BorderLayout.CENTER);
                        contentArea.revalidate();
                        contentArea.repaint();
                    }
                });
            } else if (item.equals("Settings")) {
                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        contentArea.removeAll();
                        contentArea.add(new JLabel("Settings Section", JLabel.CENTER), BorderLayout.CENTER);
                        contentArea.revalidate();
                        contentArea.repaint();
                    }
                });
            } 
            else if (item.equals("Logout")) {
                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                        new LoginPage(); // back to login page
                    }
                });
            }

            sidebar.add(btn);
        }

        mainPanel.add(sidebar, BorderLayout.WEST);

        // ===== CONTENT AREA =====
        contentArea = new JPanel();
        contentArea.setLayout(new BorderLayout());
        contentArea.setBackground(Color.WHITE);

        JLabel welcomeMsg = new JLabel("Welcome, Admin!", JLabel.CENTER);
        welcomeMsg.setFont(new Font("Segoe UI", Font.BOLD, 20));
        welcomeMsg.setForeground(new Color(25, 25, 112));
        contentArea.add(welcomeMsg, BorderLayout.CENTER);

        mainPanel.add(contentArea, BorderLayout.CENTER);

        // ===== FOOTER =====
        JLabel footer = new JLabel("Developed by Sumit Kumar © 2025", JLabel.CENTER);
        footer.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        footer.setForeground(Color.LIGHT_GRAY);
        footer.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mainPanel.add(footer, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }
}

// ===== CUSTOM ROUNDED BUTTON CLASS =====
class RoundedButton extends JButton {
    private static final int RADIUS = 20;

    public RoundedButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (getModel().isArmed()) {
            g2.setColor(getBackground().darker());
        } else {
            g2.setColor(getBackground());
        }
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), RADIUS, RADIUS);

        FontMetrics fm = g2.getFontMetrics();
        Rectangle stringBounds = fm.getStringBounds(getText(), g2).getBounds();
        int textX = (getWidth() - stringBounds.width) / 2;
        int textY = (getHeight() - stringBounds.height) / 2 + fm.getAscent();

        g2.setColor(getForeground());
        g2.setFont(getFont());
        g2.drawString(getText(), textX, textY);

        g2.dispose();
    }

    @Override
    public void setBorder(Border border) {
        // ignore
    }
}

// ===== EXAMPLE NEW PANEL CLASS (new file bhi bana sakte ho) =====
// class AddStudentPanel extends JPanel {
//     public AddStudentPanel() {
//         setLayout(new GridLayout(3, 2, 10, 10));
//         setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
//         setBackground(Color.WHITE);

//         add(new JLabel("Student Name:"));
//         add(new JTextField());

//         add(new JLabel("Roll No:"));
//         add(new JTextField());

//         add(new JLabel("Class:"));
//         add(new JTextField());
//     }
// }
