import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class ViewReportPanel extends JPanel {

    private JComboBox<String> courseCombo;
    private JTextField rollSearchField;
    private JButton searchBtn;
    private JTable studentTable;
    private DefaultTableModel tableModel;

    private static final Map<String, List<String>> courseSubjects = new HashMap<>();
    static {
        courseSubjects.put("BCA", Arrays.asList("C Programming","DBMS","Java","Computer Networks","Operating System"));
        courseSubjects.put("B.Tech", Arrays.asList("Maths","Physics","Chemistry","Electronics","Computer"));
        courseSubjects.put("MCA", Arrays.asList("Python","DBMS","Java","Data Structures","OS"));
        courseSubjects.put("MBA", Arrays.asList("Management","Accounting","Economics","Marketing","Business Law"));
    }

    public ViewReportPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245,247,250));

        // ===== Gradient Header Panel =====
        JPanel mainHeader = new JPanel(new BorderLayout());

        // Gradient Title
        JPanel header = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(25,25,112),
                        getWidth(), getHeight(), new Color(65,105,225));
                g2d.setPaint(gp);
                g2d.fillRect(0,0,getWidth(),getHeight());
            }
        };
        header.setPreferredSize(new Dimension(0,60));
        JLabel title = new JLabel("View Reports", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        header.setLayout(new BorderLayout());
        header.add(title, BorderLayout.CENTER);
        mainHeader.add(header, BorderLayout.NORTH);

        // Top panel for course + roll search
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,15,10));
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createTitledBorder("Select Course / Search by Roll No"));

        courseCombo = new JComboBox<>(courseSubjects.keySet().toArray(new String[0]));
        topPanel.add(new JLabel("Course:"));
        topPanel.add(courseCombo);

        rollSearchField = new JTextField(10);
        topPanel.add(new JLabel("Roll No:"));
        topPanel.add(rollSearchField);

        searchBtn = new JButton("Search");
        styleButton(searchBtn,new Color(70,130,180));
        topPanel.add(searchBtn);

        mainHeader.add(topPanel, BorderLayout.SOUTH);

        // Add mainHeader to NORTH
        add(mainHeader, BorderLayout.NORTH);

        // ===== Table =====
        tableModel = new DefaultTableModel();
        studentTable = new JTable(tableModel);
        studentTable.setFont(new Font("Segoe UI", Font.PLAIN,14));
        studentTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        add(scrollPane, BorderLayout.CENTER);

        // ===== Actions =====
        courseCombo.addActionListener(e -> loadStudentsByCourse((String)courseCombo.getSelectedItem()));
        searchBtn.addActionListener(e -> searchByRoll());

        loadStudentsByCourse((String)courseCombo.getSelectedItem());
    }

    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI",Font.BOLD,14));
        btn.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
    }

    private void loadStudentsByCourse(String course) {
        List<String[]> studentList = new ArrayList<>();
        File file = new File("students.csv");
        if(!file.exists()){
            JOptionPane.showMessageDialog(this,"students.csv not found!","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }

        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while((line = br.readLine()) != null){
                String[] parts = line.split(",");
                if(parts.length>=6 && parts[4].equalsIgnoreCase(course)){
                    studentList.add(parts);
                }
            }
        } catch(Exception ex){ ex.printStackTrace(); }

        List<String> subjects = courseSubjects.getOrDefault(course, Arrays.asList("Math","Science","English","Hindi","Computer"));

        List<String> columns = new ArrayList<>(Arrays.asList("Roll No","Name","Phone","Email","Course","Year"));
        columns.addAll(subjects);
        columns.addAll(Arrays.asList("Total","Percentage","Grade"));

        tableModel.setDataVector(new Object[0][0], columns.toArray());

        if(studentList.isEmpty()){
            tableModel.addRow(new Object[]{"No record found"});
        } else {
            Map<String,String[]> gradesMap = loadGradesMap();

            for(String[] student : studentList){
                List<Object> row = new ArrayList<>();
                for(int i=0;i<6;i++) row.add(student[i]);

                String roll = student[0];
                if(gradesMap.containsKey(roll)){
                    String[] marks = gradesMap.get(roll);
                    for(String m : marks) row.add(m);
                } else {
                    for(int i=0;i<subjects.size()+3;i++) row.add("");
                }

                tableModel.addRow(row.toArray());
            }
        }
    }

    private Map<String,String[]> loadGradesMap(){
        Map<String,String[]> map = new HashMap<>();
        File file = new File("grades.csv");
        if(!file.exists()) return map;

        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            while((line=br.readLine())!=null){
                String[] parts = line.split(",");
                String roll = parts[0];
                String[] marks = Arrays.copyOfRange(parts,6,parts.length);
                map.put(roll, marks);
            }
        } catch(Exception ex){ ex.printStackTrace(); }
        return map;
    }

    private void searchByRoll(){
        String roll = rollSearchField.getText().trim();
        if(roll.isEmpty()) return;

        String foundCourse = null;
        String[] studentData = null;
        try(BufferedReader br = new BufferedReader(new FileReader("students.csv"))){
            String line;
            while((line=br.readLine())!=null){
                String[] parts = line.split(",");
                if(parts[0].equalsIgnoreCase(roll)){
                    studentData = parts;
                    foundCourse = parts[4];
                    break;
                }
            }
        } catch(Exception ex){ ex.printStackTrace(); }

        if(studentData==null){
            JOptionPane.showMessageDialog(this,"Student not found!","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }

        courseCombo.setSelectedItem(foundCourse);

        List<String> subjects = courseSubjects.getOrDefault(foundCourse, Arrays.asList("Math","Science","English","Hindi","Computer"));
        List<String> columns = new ArrayList<>(Arrays.asList("Roll No","Name","Phone","Email","Course","Year"));
        columns.addAll(subjects);
        columns.addAll(Arrays.asList("Total","Percentage","Grade"));
        tableModel.setDataVector(new Object[0][0], columns.toArray());

        Map<String,String[]> gradesMap = loadGradesMap();
        List<Object> row = new ArrayList<>();
        for(int i=0;i<6;i++) row.add(studentData[i]);

        if(gradesMap.containsKey(roll)){
            String[] marks = gradesMap.get(roll);
            for(String m : marks) row.add(m);
        } else {
            for(int i=0;i<subjects.size()+3;i++) row.add("");
        }

        tableModel.addRow(row.toArray());
    }

    private String calculateGrade(double perc){
        if(perc>=90) return "A+";
        else if(perc>=80) return "A";
        else if(perc>=70) return "B";
        else if(perc>=60) return "C";
        else if(perc>=50) return "D";
        else return "F";
    }

    // ===== Test Frame =====
    public static void main(String[] args){
        JFrame frame = new JFrame("View Report Panel");
        frame.setSize(900,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ViewReportPanel());
        frame.setVisible(true);
    }
}
