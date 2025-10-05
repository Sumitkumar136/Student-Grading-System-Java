import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class AddStudentPanel extends JPanel {

    private JTextField nameField, emailField, rollField;
    private JComboBox<String> courseBox, yearBox;
    private JButton saveBtn, resetBtn;
    private JTextField phoneField;


    // Course codes
    private static final Map<String, String> courseCodes = new HashMap<>();
    static {
        courseCodes.put("BCA", "1001");
        courseCodes.put("B.Tech", "1002");
        courseCodes.put("MCA", "1003");
        courseCodes.put("MBA", "1004");
    }

    public AddStudentPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Title
        // JLabel title = new JLabel("Add New Student", JLabel.CENTER);
        // title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        // title.setForeground(new Color(25, 25, 112));
        // title.setBorder(BorderFactory.createEmptyBorder(15, 0, 20, 0));
        // add(title, BorderLayout.NORTH);

        // ===== Header with Gradient =====
        JPanel header = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(25, 25, 112),
                        getWidth(), getHeight(), new Color(65, 105, 225));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        JLabel title = new JLabel("Add New Student", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        header.add(title, BorderLayout.CENTER);
        header.setPreferredSize(new Dimension(100, 60));
        add(header, BorderLayout.NORTH);


        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fields
        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nameField = new JTextField(25);

        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        phoneField = new JTextField(25);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailField = new JTextField(25);

        JLabel courseLabel = new JLabel("Course:");
        courseLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        courseBox = new JComboBox<>(new String[]{"BCA", "B.Tech", "MCA", "MBA"});
        courseBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel yearLabel = new JLabel("Year:");
        yearLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        yearBox = new JComboBox<>(new String[]{"1st Year", "2nd Year", "3rd Year", "Final Year"});
        yearBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel rollLabel = new JLabel("Generated Roll No:");
        rollLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rollField = new JTextField(25);
        rollField.setEditable(false); // User cannot type

        // Buttons
        saveBtn = new JButton("Save");
        saveBtn.setBackground(new Color(70, 130, 180));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveBtn.setFocusPainted(false);

        resetBtn = new JButton("Reset");
        resetBtn.setBackground(new Color(220, 20, 60));
        resetBtn.setForeground(Color.WHITE);
        resetBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        resetBtn.setFocusPainted(false);

        // Layout
        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(nameLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; formPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(phoneLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; formPanel.add(phoneField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(emailLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; formPanel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(courseLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3; formPanel.add(courseBox, gbc);

        gbc.gridx = 0; gbc.gridy = 4; formPanel.add(yearLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 4; formPanel.add(yearBox, gbc);

        gbc.gridx = 0; gbc.gridy = 5; formPanel.add(rollLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 5; formPanel.add(rollField, gbc);

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.WHITE);
        btnPanel.add(saveBtn);
        btnPanel.add(resetBtn);

        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        formPanel.add(btnPanel, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Actions
        resetBtn.addActionListener(e -> {
            nameField.setText("");
            emailField.setText("");
            rollField.setText("");
            courseBox.setSelectedIndex(0);
            yearBox.setSelectedIndex(0);
        });

        saveBtn.addActionListener(e -> saveStudent());
    }

    // Save student data into CSV
    private void saveStudent() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        String course = (String) courseBox.getSelectedItem();
        String year = (String) yearBox.getSelectedItem();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        File file = new File("students.csv");

        // ✅ Check for duplicate phone number
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length > 2 && parts[2].equals(phone)) { // phone stored at index 2
                        JOptionPane.showMessageDialog(this, "Phone number already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String courseCode = courseCodes.get(course);
        String rollNo = generateRollNumber(courseCode);
        rollField.setText(rollNo);

        try (FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)) {

            out.println(rollNo + "," + name + "," + phone + "," + email + "," + course + "," + year);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JOptionPane.showMessageDialog(this, "Student Saved!\nRoll No: " + rollNo,
                "Success", JOptionPane.INFORMATION_MESSAGE);

        // ✅ Clear form after save
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
        rollField.setText("");
        courseBox.setSelectedIndex(0);
        yearBox.setSelectedIndex(0);
    }

    // Generate auto increment roll number
    private String generateRollNumber(String courseCode) {
        int max = 0;
        File file = new File("students.csv");

        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.startsWith(courseCode)) {
                        String rollStr = line.split(",")[0];
                        int num = Integer.parseInt(rollStr.substring(courseCode.length()));
                        if (num > max) max = num;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return courseCode + String.format("%02d", max + 1);
    }
}
