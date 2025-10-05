import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.*;

public class StatisticsPanel extends JPanel {

    private JComboBox<String> courseComboBox;
    private JTable overallTable, subjectTable;
    private DefaultTableModel overallModel, subjectModel;

    private Map<String, java.util.List<String>> courseSubjects = new HashMap<>();

    public StatisticsPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

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
        JLabel title = new JLabel("Student Grade Statistics", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        header.add(title, BorderLayout.CENTER);
        header.setPreferredSize(new Dimension(100, 60));
        add(header, BorderLayout.NORTH);

        // ===== Initialize course subjects =====
        courseSubjects.put("BCA", Arrays.asList("C Programming", "DBMS", "Java", "Computer Networks", "Operating System"));
        courseSubjects.put("B.Tech", Arrays.asList("Physics", "Chemistry", "Maths", "Electronics", "Engineering Drawing"));
        courseSubjects.put("MCA", Arrays.asList("Python", "DBMS", "Java", "Data Structures", "OS"));
        courseSubjects.put("MBA", Arrays.asList("Management", "Accounting", "Economics", "Marketing", "Business Law"));

        // ===== Course Selection Panel =====
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createTitledBorder("Select Course"));
        courseComboBox = new JComboBox<>(courseSubjects.keySet().toArray(new String[0]));
        topPanel.add(new JLabel("Course:"));
        topPanel.add(courseComboBox);
        add(topPanel, BorderLayout.CENTER); // yeh change hoga

        // ===== Center Panel: Tables =====
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        centerPanel.setBackground(Color.WHITE);

        overallModel = new DefaultTableModel(new Object[]{"Course", "Students", "Avg Marks", "Max", "Min", "Pass %"}, 0);
        overallTable = new JTable(overallModel);
        centerPanel.add(new JScrollPane(overallTable));

        subjectModel = new DefaultTableModel(new Object[]{"Subject", "Avg Marks", "Max", "Min", "Fail Count"}, 0);
        subjectTable = new JTable(subjectModel);
        centerPanel.add(new JScrollPane(subjectTable));

        // ===== Content Panel (holds topPanel + centerPanel vertically) =====
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.add(topPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10))); // thoda gap
        contentPanel.add(centerPanel);

        add(contentPanel, BorderLayout.CENTER);

        // ===== Action =====
        courseComboBox.addActionListener(e -> updateStatistics());

        // Initialize first course stats
        updateStatistics();
    }

    private void updateStatistics() {
        String selectedCourse = (String) courseComboBox.getSelectedItem();
        if(selectedCourse == null) return;

        java.util.List<String> subjects = courseSubjects.get(selectedCourse);
        if(subjects == null) return;

        // Read grades.csv
        java.util.List<String[]> gradeList = new ArrayList<>();
        File gradeFile = new File("grades.csv");
        if(gradeFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(gradeFile))) {
                String line;
                while((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if(parts.length >= 14 && parts[4].equalsIgnoreCase(selectedCourse)) {
                        gradeList.add(parts);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // Overall Stats
        overallModel.setRowCount(0);
        int totalStudents = gradeList.size();
        double avgTotal = 0;
        int maxTotal = Integer.MIN_VALUE;
        int minTotal = Integer.MAX_VALUE;
        int passCount = 0;

        for(String[] student : gradeList) {
            int totalMarks = 0;
            for(int i = 6; i < 6 + subjects.size(); i++) {
                totalMarks += Integer.parseInt(student[i]);
            }
            avgTotal += totalMarks;
            maxTotal = Math.max(maxTotal, totalMarks);
            minTotal = Math.min(minTotal, totalMarks);
            double perc = totalMarks / (double) subjects.size();
            if(perc >= 50) passCount++;
        }

        if(totalStudents > 0) {
            avgTotal = avgTotal / totalStudents;
            double passPerc = (passCount * 100.0) / totalStudents;
            overallModel.addRow(new Object[]{selectedCourse, totalStudents, String.format("%.2f", avgTotal), maxTotal, minTotal, String.format("%.2f", passPerc)+"%"});
        } else {
            overallModel.addRow(new Object[]{selectedCourse, 0, 0, 0, 0, "0%"});
        }

        // Subject-wise Stats
        subjectModel.setRowCount(0);
        for(int j=0;j<subjects.size();j++){
            String sub = subjects.get(j);
            double sum=0;
            int max=Integer.MIN_VALUE;
            int min=Integer.MAX_VALUE;
            int failCount=0;
            for(String[] student : gradeList){
                int marks=Integer.parseInt(student[6+j]);
                sum+=marks;
                max=Math.max(max, marks);
                min=Math.min(min, marks);
                if(marks<50) failCount++;
            }
            int count = gradeList.size();
            double avg = count>0 ? sum/count : 0;
            subjectModel.addRow(new Object[]{sub, String.format("%.2f", avg), max==Integer.MIN_VALUE?0:max, min==Integer.MAX_VALUE?0:min, failCount});
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Statistics Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.add(new StatisticsPanel());
        frame.setVisible(true);
    }
}
