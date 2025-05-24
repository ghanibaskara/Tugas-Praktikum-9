import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.geom.RoundRectangle2D;

import javax.sound.sampled.Line;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.text.*; // Import DocumentFilter classes

import components.panel.Panel;

public class App {
    public static void main(String[] args) throws Exception {

        MataKuliah matkulASD = new ASD();
        MataKuliah matkulPemlan = new Pemlan();
        MataKuliah matkulMatkomlan = new Matkomlan();
        MataKuliah matkulProbstat = new Probstat();

        //Frame Utama
        JFrame mainFrame = new JFrame();
        mainFrame.setUndecorated(true);
        mainFrame.setBackground(new Color(50, 50, 50, 250));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        mainFrame.setLocationRelativeTo(null); 
        mainFrame.setSize(420,520);
        mainFrame.setTitle("Aplikasi Penghitung Nilai");
        mainFrame.setResizable(false);
        mainFrame.setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.Y_AXIS));
        mainFrame.setShape(new RoundRectangle2D.Double(0,0, mainFrame.getWidth(), mainFrame.getHeight(), 20, 20)); 

        //Panel Title
        Panel titlePanel = new Panel(32);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));

        JLabel title = new JLabel();
        title.setText("Aplikasi Penghitung Nilai");
        title.setForeground(Color.white);
        title.setFont(new Font("Outfit", Font.PLAIN, 12));
        title.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 20, 17, 176)); 

        final Point[] mousePoint = {null};
        title.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                mousePoint[0] = evt.getPoint();
            }
        });

        title.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                int x = evt.getXOnScreen() - mousePoint[0].x;
                int y = evt.getYOnScreen() - mousePoint[0].y;
                mainFrame.setLocation(x, y);
            }
        });

        JButton minimizeButton = new JButton();
        minimizeButton.setText("-");
        minimizeButton.setFont(new Font("Outfit", Font.PLAIN, 24));
        minimizeButton.setForeground(Color.white);
        minimizeButton.setFocusPainted(false);
        minimizeButton.setContentAreaFilled(false); // Ensure transparency always
        minimizeButton.setOpaque(false);
        minimizeButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(7, 18, 10, 15)); // Default: no border

        minimizeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                minimizeButton.setBackground(new Color(60, 60, 60, 150));
                minimizeButton.setOpaque(true);
                
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                minimizeButton.setBackground(new Color(0, 0, 0, 0));
                minimizeButton.setOpaque(false);
               
            }
        });
        

        minimizeButton.addActionListener(e -> {
            mainFrame.setState(JFrame.ICONIFIED);
        });

        JButton exitButton = new JButton();
        exitButton.setText("X");
        exitButton.setFont(new Font("Outfit", Font.PLAIN, 12));
        exitButton.setForeground(Color.white);
        exitButton.setFocusPainted(false);
        exitButton.setContentAreaFilled(false); // Ensure transparency always
        exitButton.setOpaque(false);
        exitButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(16, 19, 16, 19)); // Default: no border

        exitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                exitButton.setBackground(new Color(255, 0, 0, 150));
                exitButton.setOpaque(true);

            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                exitButton.setBackground(new Color(0, 0, 0, 0));
                exitButton.setOpaque(false);

            }
        });


        exitButton.addActionListener(e -> {
            System.exit(0);
        });

        titlePanel.add(title);
        titlePanel.add(minimizeButton);
        titlePanel.add(exitButton);

        mainFrame.add(titlePanel);

        JLayeredPane mainPanel = new JLayeredPane();
        mainPanel.setBackground(new Color(0, 0, 0, 0));
        
        JPanel sidebar = new JPanel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setColor(new Color(30, 30, 30, 225));
                int arc = 45; // radius sudut
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
                g2.dispose();
            }
        };
        sidebar.setOpaque(false);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(javax.swing.BorderFactory.createEmptyBorder(45, 24, 10, 10)); // Tambahkan padding atas agar isi sidebar di bawah toggleSidebar
        sidebar.setBounds(-180, 0, 180, 490); // Mulai dari luar layar (tersembunyi)

        // Tombol toggle sidebar
        JButton toggleSidebar = new JButton("☰");
        toggleSidebar.setFont(new Font("", Font.PLAIN, 24));
        toggleSidebar.setFocusPainted(false);
        toggleSidebar.setBackground(new Color(80, 80, 80, 200));
        toggleSidebar.setForeground(Color.WHITE);
        toggleSidebar.setBorder(new javax.swing.border.LineBorder(new Color(0, 0, 0, 0), 2, true)); 
        toggleSidebar.setBounds(5, 0, 40, 40);
        toggleSidebar.setOpaque(false);
        toggleSidebar.setContentAreaFilled(false);
        mainPanel.add(toggleSidebar, JLayeredPane.PALETTE_LAYER);

         toggleSidebar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                toggleSidebar.setBackground(new Color(60, 60, 60, 100));
                toggleSidebar.setOpaque(true);
                toggleSidebar.setBorder(new javax.swing.border.LineBorder(new Color(0, 0, 0, 0), 2, true)); // border radius saat hover
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                toggleSidebar.setBackground(new Color(0, 0, 0, 0));
                toggleSidebar.setOpaque(false);
               toggleSidebar.setBorder(new javax.swing.border.LineBorder(new Color(0, 0, 0, 0), 2, true));
            }
        });

        // Animasi sliding sidebar
        final boolean[] sidebarVisible = {false};
        final javax.swing.Timer[] timer = new javax.swing.Timer[1];
        toggleSidebar.addActionListener(evt -> {
            if (timer[0] != null && timer[0].isRunning()) return;
            sidebarVisible[0] = !sidebarVisible[0];
            timer[0] = new javax.swing.Timer(1, null);
            timer[0].addActionListener(e -> {
                int x = sidebar.getX();
                if (sidebarVisible[0] ) {
                    if (x > -40) x += 1; else x+=8; 
                    sidebar.setLocation(x, 0);
                    if (x == -16) timer[0].stop();
                } else if (!sidebarVisible[0]) {
                    x -= 8;
                    sidebar.setLocation(x, 0);
                    if (x == -184) timer[0].stop();
                } 
                sidebar.repaint();
            });
            timer[0].start();
        });

        JRadioButton ASD = new JRadioButton("ASD") {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                if (isSelected()) {
                    g.setColor(new Color(80, 80, 80, 150));
                    g.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                } else if (getModel().isRollover()) {
                    g.setColor(new Color(60, 60, 60, 100));
                    g.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                }
                super.paintComponent(g);
            }
        };
        ASD.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 99));
        ASD.setOpaque(false);
        ASD.setFont(new Font("Outfit", Font.PLAIN, 16));
        ASD.setForeground(Color.white);
        ASD.setFocusPainted(false);
        ASD.setSelected(true);
        ASD.setIcon(new javax.swing.ImageIcon(new java.awt.image.BufferedImage(1, 1, java.awt.image.BufferedImage.TYPE_INT_ARGB)));
        ASD.setSelectedIcon(new javax.swing.ImageIcon(new java.awt.image.BufferedImage(1, 1, java.awt.image.BufferedImage.TYPE_INT_ARGB)));


        JRadioButton Pemlan = new JRadioButton("Pemlan") {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                if (isSelected()) {
                    g.setColor(new Color(80, 80, 80, 150));
                    g.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                } else if (getModel().isRollover()) {
                    g.setColor(new Color(60, 60, 60, 100));
                    g.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                }
                super.paintComponent(g);
            }
        };
        Pemlan.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 75));
        Pemlan.setOpaque(false);
        Pemlan.setFont(new Font("Outfit", Font.PLAIN, 16));
        Pemlan.setForeground(Color.white);
        Pemlan.setFocusPainted(false);
        Pemlan.setIcon(new javax.swing.ImageIcon(new java.awt.image.BufferedImage(1, 1, java.awt.image.BufferedImage.TYPE_INT_ARGB)));
        Pemlan.setSelectedIcon(new javax.swing.ImageIcon(new java.awt.image.BufferedImage(1, 1, java.awt.image.BufferedImage.TYPE_INT_ARGB)));


        JRadioButton Matkomlan = new JRadioButton("Matkomlan") {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                if (isSelected()) {
                    g.setColor(new Color(80, 80, 80, 150));
                    g.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                } else if (getModel().isRollover()) {
                    g.setColor(new Color(60, 60, 60, 100));
                    g.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                }
                super.paintComponent(g);
            }
        };
        Matkomlan.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 50));
        Matkomlan.setOpaque(false);
        Matkomlan.setFont(new Font("Outfit", Font.PLAIN, 16));
        Matkomlan.setForeground(Color.white);
        Matkomlan.setFocusPainted(false);
        Matkomlan.setIcon(new javax.swing.ImageIcon(new java.awt.image.BufferedImage(1, 1, java.awt.image.BufferedImage.TYPE_INT_ARGB)));
        Matkomlan.setSelectedIcon(new javax.swing.ImageIcon(new java.awt.image.BufferedImage(1, 1, java.awt.image.BufferedImage.TYPE_INT_ARGB)));

        
        JRadioButton Probstat = new JRadioButton("Probstat") {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                if (isSelected()) {
                    g.setColor(new Color(80, 80, 80, 150));
                    g.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                } else if (getModel().isRollover()) {
                    g.setColor(new Color(60, 60, 60, 100));
                    g.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                }
                super.paintComponent(g);
            }
        };
        Probstat.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 68));
        Probstat.setOpaque(false);
        Probstat.setFont(new Font("Outfit", Font.PLAIN, 16));
        Probstat.setForeground(Color.white);
        Probstat.setFocusPainted(false);
        Probstat.setIcon(new javax.swing.ImageIcon(new java.awt.image.BufferedImage(1, 1, java.awt.image.BufferedImage.TYPE_INT_ARGB)));
        Probstat.setSelectedIcon(new javax.swing.ImageIcon(new java.awt.image.BufferedImage(1, 1, java.awt.image.BufferedImage.TYPE_INT_ARGB)));

        javax.swing.ButtonGroup group = new javax.swing.ButtonGroup();
        group.add(ASD);
        group.add(Pemlan);
        group.add(Matkomlan);
        group.add(Probstat);

        Panel contentPanel = new Panel(468);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBounds(0, 0, 420, 468);

        JLabel hitungNilaiAkhir = new JLabel("Hitung Nilai Akhir");
        hitungNilaiAkhir.setFont(new Font("Outfit",Font.PLAIN,20));
        hitungNilaiAkhir.setForeground(Color.white);
        hitungNilaiAkhir.setBorder(BorderFactory.createEmptyBorder(10, 0, 15, 0));
        hitungNilaiAkhir.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(hitungNilaiAkhir);

        Panel panelTugas = new Panel(40);
        panelTugas.setLayout(new BoxLayout(panelTugas,BoxLayout.X_AXIS));

        JLabel Tugas = new JLabel("Tugas :");
        Tugas.setFont(new Font("Outfit", Font.PLAIN, 16));
        Tugas.setForeground(Color.white);
        Tugas.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 80));

        JTextField textFieldTugas = new JTextField(){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                // Gambar background
                g2.setColor(new Color(40, 40, 40, 150));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 32, 32);
                // Gambar border rounded
                g2.setColor(new Color(80, 80, 80, 100)); // warna border
                g2.setStroke(new java.awt.BasicStroke(2)); // ketebalan border
                g2.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 32, 32);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        textFieldTugas.setOpaque(false);
        textFieldTugas.setBackground(new Color(0, 0, 0, 0));
        textFieldTugas.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 10));
        textFieldTugas.setMaximumSize(new Dimension(100, 32));
        textFieldTugas.setFont(new Font("Outfit", Font.PLAIN, 16));
        textFieldTugas.setForeground(Color.white);
        
        // DocumentFilter untuk textFieldTugas
        PlainDocument docTugas = (PlainDocument) textFieldTugas.getDocument();
        docTugas.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches("[0-9]*")) {
                    super.insertString(fb, offset, string, attr);
                }
            }
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("[0-9]*")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });

        panelTugas.add(Tugas);
        panelTugas.add(textFieldTugas);


        // Panel Kuis
        Panel panelKuis = new Panel(40);
        panelKuis.setLayout(new BoxLayout(panelKuis, BoxLayout.X_AXIS));
        JLabel Kuis = new JLabel("Kuis :");
        Kuis.setFont(new Font("Outfit", Font.PLAIN, 16));
        Kuis.setForeground(Color.white);
        Kuis.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 92));
        JTextField textFieldKuis = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(40, 40, 40, 150));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 32, 32);
                g2.setColor(new Color(80, 80, 80, 100));
                g2.setStroke(new java.awt.BasicStroke(2));
                g2.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 32, 32);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        textFieldKuis.setOpaque(false);
        textFieldKuis.setBackground(new Color(0, 0, 0, 0));
        textFieldKuis.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 10));
        textFieldKuis.setMaximumSize(new Dimension(100, 32));
        textFieldKuis.setFont(new Font("Outfit", Font.PLAIN, 16));
        textFieldKuis.setForeground(Color.white);

        // DocumentFilter untuk textFieldKuis
        PlainDocument docKuis = (PlainDocument) textFieldKuis.getDocument();
        docKuis.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches("[0-9]*")) {
                    super.insertString(fb, offset, string, attr);
                }
            }
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("[0-9]*")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });

        panelKuis.add(Kuis);
        panelKuis.add(textFieldKuis);


        // Panel UTS
        Panel panelUTS = new Panel(40);
        panelUTS.setLayout(new BoxLayout(panelUTS, BoxLayout.X_AXIS));
        JLabel UTS = new JLabel("UTS :");
        UTS.setFont(new Font("Outfit", Font.PLAIN, 16));
        UTS.setForeground(Color.white);
        UTS.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 92));
        JTextField textFieldUTS = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(40, 40, 40, 150));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 32, 32);
                g2.setColor(new Color(80, 80, 80, 100));
                g2.setStroke(new java.awt.BasicStroke(2));
                g2.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 32, 32);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        textFieldUTS.setOpaque(false);
        textFieldUTS.setBackground(new Color(0, 0, 0, 0));
        textFieldUTS.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 10));
        textFieldUTS.setMaximumSize(new Dimension(100, 32));
        textFieldUTS.setFont(new Font("Outfit", Font.PLAIN, 16));
        textFieldUTS.setForeground(Color.white);

        // DocumentFilter untuk textFieldUTS
        PlainDocument docUTS = (PlainDocument) textFieldUTS.getDocument();
        docUTS.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches("[0-9]*")) {
                    super.insertString(fb, offset, string, attr);
                }
            }
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("[0-9]*")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });

        panelUTS.add(UTS);
        panelUTS.add(textFieldUTS);


        // Panel UAS
        Panel panelUAS = new Panel(40);
        panelUAS.setLayout(new BoxLayout(panelUAS, BoxLayout.X_AXIS));
        JLabel UAS = new JLabel("UAS :");
        UAS.setFont(new Font("Outfit", Font.PLAIN, 16));
        UAS.setForeground(Color.white);
        UAS.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 91));
        JTextField textFieldUAS = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(40, 40, 40, 150));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 32, 32);
                g2.setColor(new Color(80, 80, 80, 100));
                g2.setStroke(new java.awt.BasicStroke(2));
                g2.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 32, 32);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        textFieldUAS.setOpaque(false);
        textFieldUAS.setBackground(new Color(0, 0, 0, 0));
        textFieldUAS.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 10));
        textFieldUAS.setMaximumSize(new Dimension(100, 32));
        textFieldUAS.setFont(new Font("Outfit", Font.PLAIN, 16));
        textFieldUAS.setForeground(Color.white);

        // DocumentFilter untuk textFieldUAS
        PlainDocument docUAS = (PlainDocument) textFieldUAS.getDocument();
        docUAS.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches("[0-9]*")) {
                    super.insertString(fb, offset, string, attr);
                }
            }
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("[0-9]*")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });

        panelUAS.add(UAS);
        panelUAS.add(textFieldUAS);


        // Panel Hasil
        Panel panelHasil = new Panel(40);
        panelHasil.setLayout(new BoxLayout(panelHasil, BoxLayout.X_AXIS));
        JLabel Hasil = new JLabel("Hasil :");
        Hasil.setFont(new Font("Outfit", Font.PLAIN, 16));
        Hasil.setForeground(Color.white);
        Hasil.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 86));
        JTextField textFieldHasil = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(40, 40, 40, 150));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 32, 32);
                g2.setColor(new Color(80, 80, 80, 100));
                g2.setStroke(new java.awt.BasicStroke(2));
                g2.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 32, 32);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        textFieldHasil.setOpaque(false);
        textFieldHasil.setBackground(new Color(0, 0, 0, 0));
        textFieldHasil.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 10));
        textFieldHasil.setMaximumSize(new Dimension(100, 32));
        textFieldHasil.setFont(new Font("Outfit", Font.PLAIN, 16));
        textFieldHasil.setForeground(Color.white);
        textFieldHasil.setEditable(false);

        panelHasil.add(Hasil);
        panelHasil.add(textFieldHasil);

        // Tambahkan semua panel ke contentPanel
        contentPanel.add(panelTugas);
        contentPanel.add(panelKuis);
        contentPanel.add(panelUTS);
        contentPanel.add(panelUAS);
        contentPanel.add(panelHasil);

        Panel panelButton = new Panel(60);
        panelButton.setLayout(new BoxLayout(panelButton, BoxLayout.X_AXIS));
        panelButton.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JButton hitungButton = new JButton("Hitung"){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                // Gambar background
                g2.setColor(new Color(40, 40, 40, 150));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 32, 32);
                // Gambar border rounded
                g2.setColor(new Color(80, 80, 80, 200));
                g2.setStroke(new java.awt.BasicStroke(2));
                g2.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 32, 32);
                // Efek hover
                if (getModel().isRollover()) {
                    g2.setColor(new Color(255, 255, 255, 60));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 32, 32);
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };
        hitungButton.setFont(new Font("Outfit", Font.PLAIN, 16));
        hitungButton.setForeground(Color.white);
        hitungButton.setFocusPainted(false);
        hitungButton.setContentAreaFilled(false);
        hitungButton.setOpaque(false);
        hitungButton.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        hitungButton.setPreferredSize(new Dimension(120, 40));


        Panel nilaiPanel1 = new Panel(40);
        nilaiPanel1.setLayout(new BoxLayout(nilaiPanel1, BoxLayout.X_AXIS));

        JLabel labelASD = new JLabel("ASD :");
        labelASD.setFont(new Font("Outfit", Font.PLAIN, 12));
        labelASD.setForeground(Color.white);
        labelASD.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 30));

        JLabel labelPEMLAN = new JLabel("Pemlan :");
        labelPEMLAN.setFont(new Font("Outfit", Font.PLAIN, 12));
        labelPEMLAN.setForeground(Color.white);
        labelPEMLAN.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 30));

        JLabel labelMatkomlan = new JLabel("Matkomlan :");
        labelMatkomlan.setFont(new Font("Outfit", Font.PLAIN, 12));
        labelMatkomlan.setForeground(Color.white);
        labelMatkomlan.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 30));

        JLabel labelProbstat = new JLabel("Probstat :");
        labelProbstat.setFont(new Font("Outfit", Font.PLAIN, 12));
        labelProbstat.setForeground(Color.white);
        labelProbstat.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 50));


        nilaiPanel1.add(labelASD);
        nilaiPanel1.add(labelPEMLAN);
        nilaiPanel1.add(labelMatkomlan);
        nilaiPanel1.add(labelProbstat);

        // Ganti setiap komponen pada nilaiPanel2 menjadi JTextArea
        Panel nilaiPanel2 = new Panel(60);
        nilaiPanel2.setLayout(new BoxLayout(nilaiPanel2, BoxLayout.X_AXIS));

        JTextArea areaASD = new JTextArea(1, 6){
             @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(40, 40, 40, 150));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 32, 32);
                g2.setColor(new Color(80, 80, 80, 100));
                g2.setStroke(new java.awt.BasicStroke(2));
                g2.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 32, 32);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        areaASD.setOpaque(false);
        areaASD.setEditable(false);
        areaASD.setFont(new Font("Outfit", Font.PLAIN, 16));
        areaASD.setForeground(Color.white);
        areaASD.setBackground(new Color(40,40,40,180));
        areaASD.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        areaASD.setMaximumSize(new Dimension(100, 32));

        JTextArea areaPemlan = new JTextArea(1, 6){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(40, 40, 40, 150));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 32, 32);
                g2.setColor(new Color(80, 80, 80, 100));
                g2.setStroke(new java.awt.BasicStroke(2));
                g2.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 32, 32);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        areaPemlan.setOpaque(false);
        areaPemlan.setEditable(false);
        areaPemlan.setFont(new Font("Outfit", Font.PLAIN, 16));
        areaPemlan.setForeground(Color.white);
        areaPemlan.setBackground(new Color(40,40,40,180));
        areaPemlan.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        areaPemlan.setMaximumSize(new Dimension(100, 32));

        JTextArea areaMatkomlan = new JTextArea(1, 6){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(40, 40, 40, 150));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 32, 32);
                g2.setColor(new Color(80, 80, 80, 100));
                g2.setStroke(new java.awt.BasicStroke(2));
                g2.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 32, 32);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        areaMatkomlan.setOpaque(false);
        areaMatkomlan.setEditable(false);
        areaMatkomlan.setFont(new Font("Outfit", Font.PLAIN, 16));
        areaMatkomlan.setForeground(Color.white);
        areaMatkomlan.setBackground(new Color(40,40,40,180));
        areaMatkomlan.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        areaMatkomlan.setMaximumSize(new Dimension(100, 32));

        JTextArea areaProbstat = new JTextArea(1, 6){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(40, 40, 40, 150));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 32, 32);
                g2.setColor(new Color(80, 80, 80, 100));
                g2.setStroke(new java.awt.BasicStroke(2));
                g2.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 32, 32);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        areaProbstat.setOpaque(false);
        areaProbstat.setEditable(false);
        areaProbstat.setFont(new Font("Outfit", Font.PLAIN, 16));
        areaProbstat.setForeground(Color.white);
        areaProbstat.setBackground(new Color(40,40,40,180));
        areaProbstat.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        areaProbstat.setMaximumSize(new Dimension(100, 32));

        nilaiPanel2.add(Box.createHorizontalStrut(3));
        nilaiPanel2.add(areaASD);
        nilaiPanel2.add(Box.createHorizontalStrut(10));
        nilaiPanel2.add(areaPemlan);
        nilaiPanel2.add(Box.createHorizontalStrut(10));
        nilaiPanel2.add(areaMatkomlan);
        nilaiPanel2.add(Box.createHorizontalStrut(10));
        nilaiPanel2.add(areaProbstat);

        JButton tampilkanNilaiButton = new JButton("Tampilkan Nilai Semua Matkul"){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                // Gambar background
                g2.setColor(new Color(40, 40, 40, 150));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 32, 32);
                // Gambar border rounded
                g2.setColor(new Color(80, 80, 80, 200));
                g2.setStroke(new java.awt.BasicStroke(2));
                g2.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 32, 32);
                // Efek hover
                if (getModel().isRollover()) {
                    g2.setColor(new Color(255, 255, 255, 60));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 32, 32);
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };
        tampilkanNilaiButton.setFont(new Font("Outfit", Font.PLAIN, 16));
        tampilkanNilaiButton.setForeground(Color.white);
        tampilkanNilaiButton.setFocusPainted(false);
        tampilkanNilaiButton.setContentAreaFilled(false);
        tampilkanNilaiButton.setOpaque(false);
        tampilkanNilaiButton.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        tampilkanNilaiButton.setPreferredSize(new Dimension(300, 40));

        Panel panelButton2 = new Panel(60);
        panelButton2.setLayout(new BoxLayout(panelButton2, BoxLayout.X_AXIS));
        panelButton2.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        panelButton2.add(Box.createHorizontalGlue());
        panelButton2.add(tampilkanNilaiButton);
        panelButton2.add(Box.createHorizontalGlue());

        panelButton.add(Box.createHorizontalGlue());
        panelButton.add(hitungButton);
        panelButton.add(Box.createHorizontalGlue());

        contentPanel.add(panelButton);
        contentPanel.add(nilaiPanel1);
        contentPanel.add(nilaiPanel2);
        contentPanel.add(panelButton2);

        sidebar.add(ASD);
        sidebar.add(Pemlan);
        sidebar.add(Matkomlan);
        sidebar.add(Probstat);

        // Event handler untuk tombol Hitung (selalu aktif)
        hitungButton.addActionListener(e -> {
            // Bersihkan hasil
            textFieldHasil.setText("");
            areaASD.setText("");
            areaPemlan.setText("");
            areaMatkomlan.setText("");
            areaProbstat.setText("");

            // Validasi input kosong
            if (textFieldTugas.getText().isEmpty() || textFieldKuis.getText().isEmpty() || textFieldUTS.getText().isEmpty() || textFieldUAS.getText().isEmpty()) {
                textFieldHasil.setText("Error");
                return;
            }
            
                double tugas = Double.parseDouble(textFieldTugas.getText());
                double kuis = Double.parseDouble(textFieldKuis.getText());
                double uts = Double.parseDouble(textFieldUTS.getText());
                double uas = Double.parseDouble(textFieldUAS.getText());
             
               if (ASD.isSelected()) {
                    matkulASD.hitungNilaiAkhir(tugas, kuis, uts, uas);
                    textFieldHasil.setText(String.format("%.2f", matkulASD.getHasil()));
                } else if (Pemlan.isSelected()) {
                   matkulPemlan.hitungNilaiAkhir(tugas, kuis, uts, uas);
                   textFieldHasil.setText(String.format("%.2f", matkulPemlan.getHasil()));
                } else if (Matkomlan.isSelected()) {
                  matkulMatkomlan.hitungNilaiAkhir(tugas, kuis, uts, uas);
                  textFieldHasil.setText(String.format("%.2f", matkulMatkomlan.getHasil()));
                } else if (Probstat.isSelected()) {
                   matkulProbstat.hitungNilaiAkhir(tugas, kuis, uts, uas);
                   textFieldHasil.setText(String.format("%.2f", matkulProbstat.getHasil()));
               }


        });

        tampilkanNilaiButton.addActionListener(e -> {
                
                areaASD.setText(String.format("%.2f", matkulASD.getHasil()));
                areaPemlan.setText(String.format("%.2f", matkulPemlan.getHasil()));
                areaMatkomlan.setText(String.format("%.2f", matkulMatkomlan.getHasil()));
                areaProbstat.setText(String.format("%.2f", matkulProbstat.getHasil()));
            
        });

        // Event handler radio button: clear input saat ganti matkul
        ASD.addActionListener(e -> {
            textFieldTugas.setText("");
            textFieldKuis.setText("");
            textFieldUTS.setText("");
            textFieldUAS.setText("");
            textFieldHasil.setText("");
            areaASD.setText("");
            areaPemlan.setText("");
            areaMatkomlan.setText("");
            areaProbstat.setText("");
        });
        Pemlan.addActionListener(e -> {
            textFieldTugas.setText("");
            textFieldKuis.setText("");
            textFieldUTS.setText("");
            textFieldUAS.setText("");
            textFieldHasil.setText("");
            areaASD.setText("");
            areaPemlan.setText("");
            areaMatkomlan.setText("");
            areaProbstat.setText("");
        });
        Matkomlan.addActionListener(e -> {
            textFieldTugas.setText("");
            textFieldKuis.setText("");
            textFieldUTS.setText("");
            textFieldUAS.setText("");
            textFieldHasil.setText("");
            areaASD.setText("");
            areaPemlan.setText("");
            areaMatkomlan.setText("");
            areaProbstat.setText("");
        });
        Probstat.addActionListener(e -> {
            textFieldTugas.setText("");
            textFieldKuis.setText("");
            textFieldUTS.setText("");
            textFieldUAS.setText("");
            textFieldHasil.setText("");
            areaASD.setText("");
            areaPemlan.setText("");
            areaMatkomlan.setText("");
            areaProbstat.setText("");
        });


        mainPanel.add(sidebar, JLayeredPane.DEFAULT_LAYER);
        mainPanel.add(contentPanel, JLayeredPane.DEFAULT_LAYER);
        mainFrame.add(mainPanel);
        mainFrame.setVisible(true); // Set visible after adding components

    }    
}
// --- OOP Mata Kuliah ---
abstract class MataKuliah {
    protected double tugas, kuis, uts, uas;
    protected double hasil = 0;
    public MataKuliah(double tugas, double kuis, double uts, double uas, double hasil) {
        this.tugas = tugas;
        this.kuis = kuis;
        this.uts = uts;
        this.uas = uas;
        this.hasil = hasil;
    }
    public abstract void hitungNilaiAkhir(double tugas, double kuis, double uts, double uas);

    public double getHasil(){
        return hasil;
    }

}
class ASD extends MataKuliah {
    public ASD() { super(0, 0, 0, 0, 0); }
    public void hitungNilaiAkhir(double tugas, double kuis, double uts, double uas) {
        this.hasil = tugas*0.2 + kuis*0.2 + uts*0.25 + uas*0.35;
    }
}
class Pemlan extends MataKuliah {
    public Pemlan() { super(0, 0, 0, 0, 0); }
    public void hitungNilaiAkhir(double tugas, double kuis, double uts, double uas) {
        this.hasil = tugas*0.25 + kuis*0.15 + uts*0.3 + uas*0.3;
    }
}
class Matkomlan extends MataKuliah {
    public Matkomlan() { super(0, 0, 0, 0, 0); }
    public void hitungNilaiAkhir(double tugas, double kuis, double uts, double uas) {
        this.hasil = tugas*0.2 + kuis*0.25 + uts*0.25 + uas*0.3;
    }
}
class Probstat extends MataKuliah {
    public Probstat() { super(0, 0, 0, 0, 0); }
    public void hitungNilaiAkhir(double tugas, double kuis, double uts, double uas) {
        this.hasil = tugas*0.15 + kuis*0.25 + uts*0.3 + uas*0.3;
    }
}

