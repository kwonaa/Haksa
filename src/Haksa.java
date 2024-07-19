// 0704
// 화면 체인지하기

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


// custom dialog (로그인 창 띄우기)
//class MyDialog extends JDialog{
//	JLabel lblID = null; // ID 레이블
//	JTextField tfID = null; // ID 텍스트 필드
//	JLabel lblPW = null; // PW 레이블
//	JPasswordField tfPW = null; // PW 텍스트 필드
//	JButton btnLogin = null; // 로그인 버튼
//	
//	public MyDialog(JFrame frame, String title) { // 생성자 // (누가 띄울 거냐, 타이틀)
//		super(frame, title); // 부모인 JDialog의 생성자 호출
//		this.setLayout(new FlowLayout()); // 레이아웃 설정
//		
//		lblID = new JLabel("ID");
//		this.add(lblID);
//		tfID = new JTextField(10);
//		this.add(tfID);
//		
//		lblPW = new JLabel("PW");
//		this.add(lblPW);
//		tfPW = new JPasswordField(10);
//		this.add(tfPW);
//		
//		btnLogin = new JButton("로그인");
//		this.add(btnLogin);
//		
//		
//		this.setSize(170,140);
//		// this.setVisible()은 코딩하지 않음
//	}
//}

public class Haksa extends JFrame{
	// 패널 추가
	JPanel panel = null; // 메뉴별 화면이 출력되는 패널
//	MyDialog dialog = null; // 로그인 다이얼로그
	
	
	public Haksa() { // 생성자
		this.setTitle("학사관리");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// 메뉴바 만들기
		JMenuBar bar = new JMenuBar();
		
		// 메뉴(탭)
		JMenu mStudent = new JMenu("학생관리"); // 학생관리 탭
		bar.add(mStudent); // menu bar에 추가
		JMenu mBook = new JMenu("도서관리"); // 도서관리 탭
		bar.add(mBook); // menu bar에 추가
		
		// 서브메뉴(menu item)
		JMenuItem miList = new JMenuItem("학생정보");
		mStudent.add(miList); // 학생관리 menu에 menu item 추가
		miList.addActionListener(new ActionListener() { // 이벤트 처리
			@Override
			public void actionPerformed(ActionEvent e) {
			    panel.removeAll(); // 모든 컴포넌트 삭제
			    panel.revalidate(); // 다시 활성화
			    panel.repaint(); // 다시 그리기
			    panel.add(new Student()); // 화면 생성 ★ // Student 클래스를 생성자로 호출
			    panel.setLayout(null); // 레이아웃 적용 안 함
			}
		});
		
		
		JMenuItem miBookRent = new JMenuItem("대출목록");
		mBook.add(miBookRent); // 도서관리 메뉴에 서브 메뉴로 대출목록 추가
		miBookRent.addActionListener(new ActionListener() { // 이벤트 처리
			@Override
			public void actionPerformed(ActionEvent e) {
			    panel.removeAll(); // 모든 컴포넌트 삭제
			    panel.revalidate(); // 다시 활성화
			    panel.repaint(); // 다시 그리기
			    panel.add(new BookRent()); // 화면 생성 ★ // BookRent 클래스를 생성자로 호출
			    panel.setLayout(null); // 레이아웃 적용 안 함
			}
		});
		
		
		// menu bar를 frame에 추가
		this.setJMenuBar(bar);
		
		// panel 생성, 추가
		this.panel = new JPanel();
//		this.panel.setBackground(Color.DARK_GRAY);
		this.add(panel); // 눈에 보이지는 않지만 패널이 꽉차게 추가됨 (기본값이 투명)
		
		
		
		this.setSize(800,600);
		this.setVisible(true);
		
		// 로그인 다이얼로그 띄우기
//		dialog = new MyDialog(this, "로그인");
//		dialog.setModal(true); // 다이얼로그를 모달 창으로 만들어줌. modal 창이 뜨면 다른 창은 사용 못함.
//		dialog.setVisible(true); // 보이게
	}
		
	public static void main(String[] args) {
		new Haksa(); // 생성자 호출 // instance 생성
	}

}
