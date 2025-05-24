import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.Timer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.text.*; 

import components.panel.Panel;

//ini tugas paling gila yang aku kerjain... damn
public class App {
    public static void main(String[] args) throws Exception {
        //instansiasi object matkul make oop inheritance & polymorphism
        MataKuliah matkulASD = new ASD();
        MataKuliah matkulPemlan = new Pemlan();
        MataKuliah matkulMatkomlan = new Matkomlan();
        MataKuliah matkulProbstat = new Probstat();

        //Frame Utama
        JFrame mainFrame = new JFrame();

        //set undecorated itu biar frame ngga ada border, title bar, dan tombol close/minimize, method ini penting buat aplikasi yang kubuat karena emang mau kubikin keliatan modern
        //karena ide pertamaku itu pengen bikin software ini mirip kaya software calculatornya windwos, kaya backgroundnya transparent gitu dan keliatan modern, ada border radius jg
        //nah buat realisasiin ide itu, setelah aku cari" ternyata setundecorrated harus true biar aku bisa manggil method" yang kubutuhin 
        //kaya dibawah ini itu ada setBacground yg make objek color yang opacitynya aku turunin, nah itu harus setUndecorative true dulu (ngga tau kenapa harus gitu, tapi saya ngikut saja)

        mainFrame.setUndecorated(true);

        //setbackground buat ngatur warna background, disini aku make custom color make instansiasi object Color
        //parameternya ada (r,g,b,a) rgb ya red green blue dan a itu semacam opacitynya, itu yg bisa bikin aplikasi ini transparan
        // mainFrame.setOpacity(0.5f);
        mainFrame.setBackground(new Color(50, 50, 50, 235));
        // mainFrame.setBackground(Color.BLACK); // bisa juga make color bawaan java
        // mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); ini ga kepake karena aku setUndecorrative true, ini semacam actionListener buat button close di window biasa, tapi karena di aplikaku gaada, aku bikin buttonnya sendiri

        //setlocationrelative itu lokasi frame ketika aplikasi dibuka, kalo null itu biar frame muncul di tengah layar
        mainFrame.setLocationRelativeTo(null); 

        //setsize ya setsize pxl atau wxh
        mainFrame.setSize(420,520);

        // mainFrame.setTitle("Aplikasi Penghitung Nilai"); ini juga ga kepake karena aku undecorrative jadi frame title bawwaan windows gaada, dan yap aku bikin sendiri nantinya
        // mainFrame.setResizable(false); sama kaya setdefaultcloseoperation dan setTitle, ini ga kepake karena aku undecorrative, jadi gaada tombol maximize, dan gabisa resize karena gapunya frame window

        //set layout itu buat ngatur layout dari komponen yang ada di dalam frame
        //disini aku make boxlayout karena ini paling mirip kaya container row/column di framework lain, layout y axis itu kaya column jadinya, karena disusun kebawah (kalo gasalah, kalo bukan row ya column hehe)
        mainFrame.setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.Y_AXIS));

        //ini juga keuntungan undecorrative, aku bisa ngasih border radius make method set shape
        mainFrame.setShape(new RoundRectangle2D.Double(0,0, mainFrame.getWidth(), mainFrame.getHeight(), 20, 20)); 

        //Panel Title
        //aku ada nyiapin class panel karena tau bakalan banyak make container, disini parameternya aku isi tinggi
        Panel titlePanel = new Panel(32);
        //layout boxlayoutnya aku jadiin x axis biar bisa disusun secara horizontal (kaya row)
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));

        //Jlabel buat title DIY yang kubikin sendiri (karena aku undecorrative)
        JLabel title = new JLabel("Aplikasi Penghitung Nilai");
        //ngubah warna teksnya jadi putih make setForeground yg parameternya color
        title.setForeground(Color.white);
        //set custom font make method setfond dengan parameter objek font yang aku instansiasi sendiri, aku make font "outfit" yg sizenya 12 disini
        title.setFont(new Font("Outfit", Font.PLAIN, 12));
        //set border ya bikin batas, disini aku make empty border biar ngga ada elemen yang ganggu, karena tujuanku itu biar posisi teks stay dikiri dan button minimize dan exit yang kubikin itu bisa dikanan
        //makanaya parameter bordernya yg right aku isi gede (oiya cara kerja border juga mirip kaya padding yaitu masuk kedalam)
        title.setBorder(BorderFactory.createEmptyBorder(15, 20, 17, 176)); 

        //lagi lagi kerepotan karena undecorrative, gaada framenya automatis gabisa aku drag windownya jadi aku harus bikin method manual
        //inisialisasi konstanta mousePoint yg tipenya array Point yg isinya null doang 
        //ini harus array karena di dalem method mouselistener/motionlistener itu nilai mousepoint udah diambil, jujurly aku bingung, yang jelas entah knp harus array aja gitu aku sendiri masih 50% paham ama logicnya, mungkin gara" nilainya ada di dalem method dan secara logika, kalo blm ada input mouse ini gapunya nilai dan gabisa diambil(?) tapi isinya juga null, so idk
        final Point[] mousePoint = {null};
        //method mouse listener di label title dengan parameter interface class mouse adapter, class ini menyimpan method" buat penganganan input mouse 
        title.addMouseListener(new MouseAdapter() {
            //override method mousepressed yang ada di mouse adapter, ini bakal dipanggil ketika mouse di klik
            //parameternya mouse event ini class yang nangkep semua input mouse ke gui salah satunya itu klik
            @Override
            public void mousePressed(MouseEvent evt) {
                //ketika mouse masih ngeklik title atau didrag, mousePoint[0] itu diisi dengan koordinat point dari mouse yang didapat dari method getPoint, ini bakal jadi patokan posisi mouse ketika di drag
                mousePoint[0] = evt.getPoint();
            }
        });

        //sama kaya diatas make interface dan override
        title.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent evt) {
                // int x sama y buat jadi koordinat window yg lagi didrag, method getx sama gety itu buat ngasih kordinat posisi kursor (bukan komponen) 
                //sedangkan mousepoint itu masih nyimpen kordinat komponen, dengan mereka dikurangin gini bakalan mastiin windownya geraknya smooth 
                // logikanya, framenya itu loncat ke koordinat mouse TAPI bagi framenya koordinat mouse itu kaya titik 0,0 (posisi kiri atas layar)
                //dengan dikurangin gini gampangnya bikin frame ngga loncat ke 0,0 (yaitu cursor di pojok kiri atas frame) tapi cursor nya ga pindah posisi
                int x = evt.getXOnScreen() - mousePoint[0].x;
                int y = evt.getYOnScreen() - mousePoint[0].y;
                //setlocation frame di layar sesuai dengan koordinat x dan y yang udah diatur dan ini bakalan kepanggil setiap tick dari framenya didrag
                mainFrame.setLocation(x, y);
            }
        });

        //bikn tombol minimize sendiri make jbutton huhuuuu
        JButton minimizeButton = new JButton("-");
        //set background warna tombolnya, aku make warna abu" gelap biar keliatan modern (tapi ga keliatan sebelum dihover karena setopaquenya false)
        minimizeButton.setBackground(new Color(60, 60, 60, 150));
        minimizeButton.setFont(new Font("Outfit", Font.PLAIN, 24));
        minimizeButton.setForeground(Color.white);
        //focus painted ini biar gada highlight
        minimizeButton.setFocusPainted(false);
        //ini biar background tombolnya transparan terus dari awal sblm dihover, fungsi ini kalo false itu memperbolehkan use ngegambar background button yang ngga default (defaultnya itu warna polos)
        minimizeButton.setContentAreaFilled(false); 
        // setopaque mirip juga sama method yg atas yg bikin komponennya bisa digambar secara bebas, tapi ini lebih kearah kalo opaque false itu bg bisa transparan
        // minimizeButton.setOpaque(false);
        minimizeButton.setBorder(BorderFactory.createEmptyBorder(7, 18, 10, 15)); // Default: no border

        //efek hover make logika yg sama kaya yg diatas
        minimizeButton.addMouseListener(new MouseAdapter() {
            //method mouse enter buat nangkep ketika cursor masuk ke area button
            @Override
            public void mouseEntered(MouseEvent evt) {
                //ketika masuk warna background diganti dan opaquenya di set true biar bgnya keliatan
                minimizeButton.setOpaque(true);
                
            }
            //sama kaya method atasnya tapi kebalikannya yaiu biar bgnya transparan setopaquenya di-false
            @Override
            public void mouseExited(MouseEvent evt) {
                minimizeButton.setOpaque(false);
               
            }
        });
        
        //action listener buat tombol minimize, ini bakal kepanggil ketika tombol di klik dan bakalan manggil method setState dari JFrame yaitu kondisi framenya diubah
        //untuk case ini paremeternyta inonified atau bisa dibilang minimize
        // e itu cuma nama variabel lambda expression yang jadi representasi objek action event (aku kurang bisa jelasin apa itu lamba expression tapi itu kaya parameter yang bentuknya udah ditentuin (di case ini, e itu objek actionevent))
        minimizeButton.addActionListener(e -> {
            mainFrame.setState(JFrame.ICONIFIED);
        });

        //buat tombol close, sama kaya minimize button, aku bikin sendiri dan semua logicnya sama, cuma beda di action listenernya aja yaitu buat exit
        JButton exitButton = new JButton("X");
        exitButton.setBackground(new Color(255, 0, 0, 150));
        exitButton.setFont(new Font("Outfit", Font.PLAIN, 12));
        exitButton.setForeground(Color.white);
        exitButton.setFocusPainted(false);
        exitButton.setContentAreaFilled(false);
        // exitButton.setOpaque(false);
        exitButton.setBorder(BorderFactory.createEmptyBorder(16, 19, 16, 19)); // Default: no border

        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                exitButton.setOpaque(true);

            }
            @Override
            public void mouseExited(MouseEvent evt) {
                exitButton.setOpaque(false);

            }
        });
        //oiya aku baru nyadar pas lagi bikin komen ini kalo di komponen bgnya itu bisa dibikin transparan (emang jframe aja ribet jirr mau transparan harus undecorrative)

        //action listener kalo diklik bakalan exit
        exitButton.addActionListener(e -> {
            System.exit(0);
        });

        //nambahin komponen ke title panel, jadi title, minimize button, dan exit button itu ditambahin ke title panel yang urutannya memanjang secara horizontal
        titlePanel.add(title);
        titlePanel.add(minimizeButton);
        titlePanel.add(exitButton);

        //add title panel ke main frame
        mainFrame.add(titlePanel);

        //ini panel utama yang bakal jadi container dari semua komponen di aplikasi ini
        //make jlayeredpane karena aku ada rencana bikin sidebar yang bisa keluar masuk, dimana sidebar itu diatas container, jadi harus berlayer layer
        JLayeredPane mainPanel = new JLayeredPane();
        mainPanel.setBackground(new Color(0, 0, 0, 0));
        
        //bikin panel sidebar
        JPanel sidebar = new JPanel() {
            //nah ini ribet banget ngl, make paint component dan graphics&2d
            //gampangnya paint component itu method yang dipanggil pertama kali ketika aplikasi dijalankan yaitu buat mem"paint" atau menggambar komponennya di UI
            //nah buat beberapa komponen, buat dapeting desain yang aku pengenin, itu ga bisa sesimpel set background atau setshape, buat panel, radiobutton,dll itu harus make paint component
            //yang jelas, dengan overriding method paint component, kita bisa custom bentuk komponennya sebebas kita
            @Override
            //parameternya class graphics yaitu class yang "menggambar" komponennya di ui
            protected void paintComponent(Graphics g) {   
                //graphics 2d itu subclassnya graphics yang isinya lebih kompleks, tapi dengan make graphics2d lebih banyak kustomisasi ui yg bisa kita bikin
                //casting graphics2d ke graphics g, terus manggil g.create, aku sendiri kurang paham logikanya, tapi g create kaya bikin copy gambarnya sblm ditaroh ke uinya yang asli
                //fungsinya biar bisa di-dispose di akhir" jadi mungkin hemat memory dan aman(?)
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(30, 30, 30, 225));
                //set radius sudut make method roundreact
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 45, 45);
                g2.dispose();

                //manggil super.paintcomponent di akhir biar ui customnya digambar dulu sebelum komponen yang lain ada di dalam komponen ini, termasuk desain defaultnya
                 super.paintComponent(g);
            }
        };
        sidebar.setOpaque(false);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(45, 24, 10, 10)); // tambahin padding atas agar biar isi sidebar di bawah button toggleSidebar
        sidebar.setBounds(-180, 0, 180, 490); // setbounds itu gabungan setlocation sama size, jadi setbounds(x,y,width,height) dan disini xnya aku bikin minus, biar komponen ini awalnya ada diluar frame atau ga keliatan (karena nanti ditampilin make tombol toggle), dan juga karena komponen ini ditaroh di layered pane, dimana layered pane itu gapunya layout, jadi aku harus ngatur posisi dan ukuran komponen ini secara manual


        
        // Tombol toggle sidebar
        JButton toggleSidebar = new JButton("☰");
        toggleSidebar.setFont(new Font("", Font.PLAIN, 24));
        toggleSidebar.setFocusPainted(false);
        toggleSidebar.setForeground(Color.WHITE);
        toggleSidebar.setBackground(new Color(70, 70, 70, 220));
        //entah knp harus dikasi lineborder biar ini tombol muncul garis 3nya, kalo ga dikasi cuma titik" doang 
        toggleSidebar.setBorder(new LineBorder(new Color(0, 0, 0, 0), 2, true)); 
        toggleSidebar.setBounds(5, 0, 40, 40);
        toggleSidebar.setOpaque(false);
        toggleSidebar.setContentAreaFilled(false);
        mainPanel.add(toggleSidebar, JLayeredPane.PALETTE_LAYER);

        //mouse listener buat hover logikanya sama kaya yg lain
         toggleSidebar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                toggleSidebar.setOpaque(true);
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                toggleSidebar.setOpaque(false);
           
            }
        });

        // Animasi sliding sidebar
        // bikin constanta boolean sidebarVisible yang awalnya false, ini buat ngatur apakah sidebar itu keliatan atau ngga, sama kaya mouse point juga entah kenapa harus array karena kalo masuk ke method actionlistener jd error
        // sama constanta timer yg array jg
        final boolean[] sidebarVisible = {false};
        final Timer[] timer = new Timer[1];
        toggleSidebar.addActionListener(evt -> {
            //kalo timer jalan & tidak null, berarti animasi lagi jalan dan bakalan direturn biar ga ngerusak animasi yang lagi jalan
            if (timer[0] != null && timer[0].isRunning()) return;
            //setelah animasi selesai, sidebarVisible itu dibalik nilainya, jadi kalo sebelumnya false jadi true, dan sebaliknya
            sidebarVisible[0] = !sidebarVisible[0];
            // inisialisasi timer baru, ini bakal jadi animasi sliding sidebar
            //delaynya itu tick method ini dijalanin sampe timernya stop, jadi kalo delaynya 5ms, berarti tiap 5ms bakal ada animasi sliding, makin gede delay, makin lambat animasi
            timer[0] = new Timer(5, null);
            // action listener buat timer, ini bakal dipanggil setiap tick dari timer
            timer[0].addActionListener(e -> {
                // ini bakal jadi posisi x dari sidebar yang disimpen di int x
                int x = sidebar.getX();
                //ketika sidebar true bakalan jalanin method dibawah ini
                if (sidebarVisible[0] ) {
                    //nah kalo koordinat x sidebar msh dibawah -40, xnya geser 8 dan ketika udh diatas 40 xnya keser cuma 1 per tick
                    //knp aku bikin ini, karena aku terinspirasi sama animasinya calculator windows jg:v yaitu animasinya melambat setelah diklik
                    if (x > -40) x += 1; else x+=8;
                    //setiap tick diset location sesuai x yang baru
                    sidebar.setLocation(x, 0);
                    //kalo udh sesuai di posisi yang dipengenin, masuk if condition dan distop timernya
                    if (x == -16) timer[0].stop();
                } else if (!sidebarVisible[0]) {
                    //logikanya sama kaya yg diatas yaitu pas kebuka, sedangkan ini proses menutupnya
                    x -= 8;
                    sidebar.setLocation(x, 0);
                    if (x == -184) timer[0].stop();
                } 
                // sidebar.repaint(); nggatau ini buat apa, katanya biar digambar ulang tapi tanpa ini masih works
            });
            //nah ketika mouse diclick, timernya bakalan jalan dan semua code diatas jalan yang bakalan ngebentuk animasi 
            timer[0].start();
        });

        //radio button buat matkul, ini juga ribet karena aku mau bikin custom radio button yang bisa di hover dan di klik DAN GAADA BULET BULETNYA
        JRadioButton ASD = new JRadioButton("ASD") {
            @Override
            protected void paintComponent(Graphics g) {
                //ini buat logic hovernya
                // super.paintComponent(g);
                if (isSelected()) {
                    g.setColor(new Color(80, 80, 80, 150));
                    g.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                    //isrollover itu buat tau ketika dihover, mirip" kaya motionlistener
                } else if (getModel().isRollover()) {
                    g.setColor(new Color(60, 60, 60, 100));
                    g.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                }
                //paint component diakhir
                super.paintComponent(g);
            }
        };
        ASD.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 99));
        ASD.setOpaque(false);
        ASD.setFont(new Font("Outfit", Font.PLAIN, 16));
        ASD.setForeground(Color.white);
        ASD.setFocusPainted(false);
        ASD.setSelected(true);
        //NAH INI MAKE ICON BUAT NGILANGIN BUNDER"NYA
        //ini make icon buffered image yang pxl nya cuma 1, jd ga keliatan :p (ini minta chat gpt, maaf (emot sujud dan minta mohon))
        ASD.setIcon(new ImageIcon(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)));
        ASD.setSelectedIcon(new ImageIcon(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)));

        //buat semua button matkul yg lain logicnya sama ya
        JRadioButton Pemlan = new JRadioButton("Pemlan") {
            @Override
            protected void paintComponent(Graphics g) {
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
        Pemlan.setIcon(new ImageIcon(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)));
        Pemlan.setSelectedIcon(new ImageIcon(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)));


        JRadioButton Matkomlan = new JRadioButton("Matkomlan") {
            @Override
            protected void paintComponent(Graphics g) {
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
        Matkomlan.setIcon(new ImageIcon(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)));
        Matkomlan.setSelectedIcon(new ImageIcon(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)));

        
        JRadioButton Probstat = new JRadioButton("Probstat") {
            @Override
            protected void paintComponent(Graphics g) {
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
        Probstat.setIcon(new ImageIcon(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)));
        Probstat.setSelectedIcon(new ImageIcon(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)));

        //masukin ke group button biar cuma bisa 1 yg aktif dalam 1 waktu
        ButtonGroup group = new ButtonGroup();
        group.add(ASD);
        group.add(Pemlan);
        group.add(Matkomlan);
        group.add(Probstat);

        //ini panel buat content fitur utama kaya masukin nilai, button hitung, output, dll
        Panel contentPanel = new Panel(468);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        //setbounds karena layeredpane gd layout
        contentPanel.setBounds(0, 0, 420, 468);

        //label "Hitung Nilai Akhir" di paling atas panel
        JLabel hitungNilaiAkhir = new JLabel("Hitung Nilai Akhir");
        hitungNilaiAkhir.setFont(new Font("Outfit",Font.PLAIN,20));
        hitungNilaiAkhir.setForeground(Color.white);
        hitungNilaiAkhir.setBorder(BorderFactory.createEmptyBorder(10, 0, 15, 0));
        hitungNilaiAkhir.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(hitungNilaiAkhir);

        //bikin panel tiap komponen nilai yg dimasukin, dimulai dr tugas
        Panel panelTugas = new Panel(40);
        panelTugas.setLayout(new BoxLayout(panelTugas,BoxLayout.X_AXIS));
        //ini labelnya
        JLabel Tugas = new JLabel("Tugas :");
        Tugas.setFont(new Font("Outfit", Font.PLAIN, 16));
        Tugas.setForeground(Color.white);
        Tugas.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 80));
        //ini textfieldnya
        JTextField textFieldTugas = new JTextField(){
            //yey make paintcomponent lagi
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                // gambar background
                g2.setColor(new Color(40, 40, 40, 150));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 32, 32);
                // gambar border rounded
                g2.setColor(new Color(80, 80, 80, 100)); // warna border
                g2.setStroke(new java.awt.BasicStroke(2)); // ketebalan border
                g2.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 32, 32); //gambar rectangle yang ada radiusnya dan abis dikasi stroke
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
        
        // DocumentFilter untuk textFieldTugas jadi cuma bisa nerima input angka dan maksimal 2 digit (0-99)
        // jujurly kurg paham cara kerjanya gimana hehe
        // yg jelas ini ada object plain document, dan dari textfieldnya diambil dan dicasting dalam bentuk document, bukan string
        PlainDocument docTugas = (PlainDocument) textFieldTugas.getDocument();
        //didalam doctugas, manggil method set document filter dengan parameter object docfilter yang bakalan disetting sendiri methodnya make overriding
        docTugas.setDocumentFilter(new DocumentFilter() {
            @Override
            //di method insertstring masuk parameter" ini yg jujur aku kurg paham maksudnya gmn, terus ngethrow exception yang berupa
            //dari yg kubaca filterbypass itu kaya sesuatu yang nentuin acc atau ngganya input sesuai filter dokumen
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                //kalo stringnya null ga lanjutin method
                if (string == null) return;
                StringBuilder sb = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
                sb.insert(offset, string);
                //kalo inputnya cocok dengan regex \\d{0,2} \\d artinya angka dan {0,2} itu artinya maksimal 2 digit bakalan manggil insertstring yang kemudian diacc oleh filterbypass dan masuk textfield
                if (sb.toString().matches("\\d{0,2}")) {
                    super.insertString(fb, offset, string, attr);
                }
            }
            //logicnya sama, taoi kalo insetstring itu ketika input awal masuk dan replace ketika teksnya diedit
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text == null) return;
                StringBuilder sb = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
                sb.replace(offset, offset + length, text);
                if (sb.toString().matches("\\d{0,2}")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });

        panelTugas.add(Tugas);
        panelTugas.add(textFieldTugas);

        //logicnya sama buat yg lain
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


        PlainDocument docKuis = (PlainDocument) textFieldKuis.getDocument();
        docKuis.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string == null) return;
                StringBuilder sb = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
                sb.insert(offset, string);
                if (sb.toString().matches("\\d{0,2}")) {
                    super.insertString(fb, offset, string, attr);
                }
            }
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text == null) return;
                StringBuilder sb = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
                sb.replace(offset, offset + length, text);
                if (sb.toString().matches("\\d{0,2}")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });

        panelKuis.add(Kuis);
        panelKuis.add(textFieldKuis);



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


        PlainDocument docUTS = (PlainDocument) textFieldUTS.getDocument();
        docUTS.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string == null) return;
                StringBuilder sb = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
                sb.insert(offset, string);
                if (sb.toString().matches("\\d{0,2}")) {
                    super.insertString(fb, offset, string, attr);
                }
            }
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text == null) return;
                StringBuilder sb = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
                sb.replace(offset, offset + length, text);
                if (sb.toString().matches("\\d{0,2}")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });

        panelUTS.add(UTS);
        panelUTS.add(textFieldUTS);


      
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

        
        PlainDocument docUAS = (PlainDocument) textFieldUAS.getDocument();
        docUAS.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string == null) return;
                StringBuilder sb = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
                sb.insert(offset, string);
                if (sb.toString().matches("\\d{0,2}")) {
                    super.insertString(fb, offset, string, attr);
                }
            }
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text == null) return;
                StringBuilder sb = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
                sb.replace(offset, offset + length, text);
                if (sb.toString().matches("\\d{0,2}")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });

        panelUAS.add(UAS);
        panelUAS.add(textFieldUAS);
        
        //ini panel buat hasil, nanti bedanya di textfield
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
        //textfield hasil nggabisa diedit
        textFieldHasil.setEditable(false);

        panelHasil.add(Hasil);
        panelHasil.add(textFieldHasil);

        // Tambahkan semua panel ke contentPanel
        contentPanel.add(panelTugas);
        contentPanel.add(panelKuis);
        contentPanel.add(panelUTS);
        contentPanel.add(panelUAS);
        contentPanel.add(panelHasil);

        //ini panel buat button hitung
        Panel panelButton = new Panel(60);
        panelButton.setLayout(new BoxLayout(panelButton, BoxLayout.X_AXIS));
        panelButton.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        //button hitung
        JButton hitungButton = new JButton("Hitung"){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                // gambar background
                g2.setColor(new Color(40, 40, 40, 150));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 32, 32);
                // corner border rounded
                g2.setColor(new Color(80, 80, 80, 200));
                g2.setStroke(new java.awt.BasicStroke(2));
                g2.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 32, 32);
                // efek hover
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

        //ini panel yang nyimpen komponen buat nampilin label matkul sblm nampilin textarea buat nilainya
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

       //panel yg nyimpen text area dari nilai akhir setiap matkul
        Panel nilaiPanel2 = new Panel(60);
        nilaiPanel2.setLayout(new BoxLayout(nilaiPanel2, BoxLayout.X_AXIS));
        //sama aja sih isinya, ribet di paintcomponent
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

        //panel buat "tampilkan nilai" button
        Panel panelButton2 = new Panel(60);
        panelButton2.setLayout(new BoxLayout(panelButton2, BoxLayout.X_AXIS));
        panelButton2.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        //createHorizontalGlue itu kaya spacer buar ngisi komponen kosong dan sizenya itu fleksibel atau dinamis, ngikutin komponen yg lain
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

        //export button buat export nilai ke file txt
        //ini button exportnya, sama aja sih paintcomponentnya
         JButton exportButton = new JButton("Export"){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                // gambar background
                g2.setColor(new Color(40, 40, 40, 150));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 32, 32);
                // corner border rounded
                g2.setColor(new Color(80, 80, 80, 200));
                g2.setStroke(new java.awt.BasicStroke(2));
                g2.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 32, 32);
                // efek hover
                if (getModel().isRollover()) {
                    g2.setColor(new Color(255, 255, 255, 60));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 32, 32);
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };
        exportButton.setFont(new Font("Outfit", Font.PLAIN, 16));
        exportButton.setForeground(Color.white);
        exportButton.setFocusPainted(false);
        exportButton.setContentAreaFilled(false);
        exportButton.setOpaque(false);
        exportButton.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        exportButton.setPreferredSize(new Dimension(120, 40));

        // action listener untuk tombol export
        exportButton.addActionListener(e -> {
            //bisa ngubah directory atau nama file sesuai keinginan disini, tapi defaultnya bakal disimpan di direktori project ini
            String directoryFile = "D:\\Project\\Java\\Tugas Praktikum 9\\Tugas Praktikum 9\\fileExport\\";
            String namaFile = "nilaiMatkul";

            //INI GABOLEH DIUBAH YAW
            String file = directoryFile + namaFile + ".txt";
            //ini file writer harus make try catch karena bisa ada error pas nulis filenya
            // dan sejujurnya aku kurang paham lagi detailnya knp kok harus try catch
            try {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write("Nilai Akhir Mata Kuliah:\n");
                fileWriter.write("ASD: " + String.format("%.2f", matkulASD.getHasil()) + "\n");
                fileWriter.write("Pemlan: " + String.format("%.2f", matkulPemlan.getHasil()) + "\n");
                fileWriter.write("Matkomlan: " + String.format("%.2f", matkulMatkomlan.getHasil()) + "\n");
                fileWriter.write("Probstat: " + String.format("%.2f", matkulProbstat.getHasil()) + "\n");
                fileWriter.close();
                
            } catch (IOException ex) {
                //kalo ada error pas nulis filenya, bakal nampilin pesan error di textfield hasil
                textFieldHasil.setText("Error");
            }
        });
    
        //also ini aku baru nambahin ketika lagi nulis komen ini karena baru notice ketika sidebar dibuka, masih bisa mencet dan hover tombol tampilkan nilainya
        //tapi entah knp uinya jadi aneh, tp gpp eh :V
        sidebar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                tampilkanNilaiButton.setEnabled(false);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                tampilkanNilaiButton.setEnabled(true);
            }
        });

        sidebar.add(ASD);
        sidebar.add(Pemlan);
        sidebar.add(Matkomlan);
        sidebar.add(Probstat);
        sidebar.add(Box.createVerticalStrut(250)); // spacer vertical
        sidebar.add(exportButton);

        // action listener untuk tombol Hitung 
        hitungButton.addActionListener(e -> {
            // Bersihkan hasil tp gjd dipake hehe
            // textFieldHasil.setText("");
            // areaASD.setText("");
            // areaPemlan.setText("");
            // areaMatkomlan.setText("");
            // areaProbstat.setText("");

            // validasi input kosong
            if (textFieldTugas.getText().isEmpty() || textFieldKuis.getText().isEmpty() || textFieldUTS.getText().isEmpty() || textFieldUAS.getText().isEmpty()) {
                textFieldHasil.setText("Error");
                return;
            }
            
            //ambil nilai input tiap textfieldnya yang diparsedouble jd nilainya bakalan jd double
                double tugas = Double.parseDouble(textFieldTugas.getText());
                double kuis = Double.parseDouble(textFieldKuis.getText());
                double uts = Double.parseDouble(textFieldUTS.getText());
                double uas = Double.parseDouble(textFieldUAS.getText());
             
                //if condition buat radiobutton yg lagi diselect, nanti nyesuain make rumus dari kelas matkul yg mana 
               if (ASD.isSelected()) {
                    matkulASD.hitungNilaiAkhir(tugas, kuis, uts, uas);
                    //terus masukin text hasilnya make string format biar ga kepanjangan
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
                //nampilin nilai semua matkul di text area masing-masing make getHasil
                areaASD.setText(String.format("%.2f", matkulASD.getHasil()));
                areaPemlan.setText(String.format("%.2f", matkulPemlan.getHasil()));
                areaMatkomlan.setText(String.format("%.2f", matkulMatkomlan.getHasil()));
                areaProbstat.setText(String.format("%.2f", matkulProbstat.getHasil()));
            
        });

        // action listener radio button buat clear input saat ganti matkul
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


        //add sisanya
        mainPanel.add(sidebar, JLayeredPane.DEFAULT_LAYER);
        mainPanel.add(contentPanel, JLayeredPane.DEFAULT_LAYER);
        mainFrame.add(mainPanel);
        mainFrame.setVisible(true); //setvisible harus di akhir setelah semua komponen dimasukin biar keliatan komponennya

    }    
}
// oop matkul make class abstract
abstract class MataKuliah {
    //access modifiernya protected biar subclassnya doang yg bisa akses
    protected double tugas, kuis, uts, uas;
    //double hasil dikasih nilai dluan biar bisa ditampilin walau blm input nilai
    protected double hasil = 0;
    //constructor
    public MataKuliah(double tugas, double kuis, double uts, double uas) {
        this.tugas = tugas;
        this.kuis = kuis;
        this.uts = uts;
        this.uas = uas;
    }
    //method abstract yg wajib dimilikin semua subclass
    public abstract void hitungNilaiAkhir(double tugas, double kuis, double uts, double uas);

    //buat ngembaliin nilai hasil
    public double getHasil(){
        return hasil;
    }

}

//subclass dari matkul yg punya method hitung nilai akhir yg beda"
class ASD extends MataKuliah {
    public ASD() { super(0, 0, 0, 0); }
    public void hitungNilaiAkhir(double tugas, double kuis, double uts, double uas) {
        this.hasil = tugas*0.2 + kuis*0.2 + uts*0.25 + uas*0.35;
    }
}
class Pemlan extends MataKuliah {
    public Pemlan() { super(0, 0, 0, 0); }
    public void hitungNilaiAkhir(double tugas, double kuis, double uts, double uas) {
        this.hasil = tugas*0.25 + kuis*0.15 + uts*0.3 + uas*0.3;
    }
}
class Matkomlan extends MataKuliah {
    public Matkomlan() { super(0, 0, 0, 0); }
    public void hitungNilaiAkhir(double tugas, double kuis, double uts, double uas) {
        this.hasil = tugas*0.2 + kuis*0.25 + uts*0.25 + uas*0.3;
    }
}
class Probstat extends MataKuliah {
    public Probstat() { super(0, 0, 0, 0); }
    public void hitungNilaiAkhir(double tugas, double kuis, double uts, double uas) {
        this.hasil = tugas*0.15 + kuis*0.25 + uts*0.3 + uas*0.3;
    }
}

