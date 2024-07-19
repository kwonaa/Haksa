// 0703
// 학사 관리 프로그램 만들기
// TextArea 표 형태로 출력하기

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Student extends JPanel { // JPanel로 바꾸기
	JLabel lblID = null; // 레이블 ID. 생성자 입장에서 전역변수
	JTextField tfID = null; // 텍스트필드 ID. 생성자 입장에서 전역변수
	JLabel lblName = null; 
	JTextField tfName = null;
	JLabel lblDept = null;
	JTextField tfDept = null;
	JLabel lblAddress = null;
	JTextField tfAddress = null;
	
	
	JButton btnInsert = null; // 등록 버튼
	JButton btnSelect = null; // 목록 버튼
	JButton btnUpdate = null; // 수정 버튼
	JButton btnDelete = null; // 삭제 버튼
	
	JButton btnSearch = null; // 검색 버튼
	
	DefaultTableModel model = null; // 테이블의 데이터를 관리하는 모델
	JTable table = null; // 테이블 컴포넌트. 학생 목록을 표 형태로 출력

	
	public Student() {
		// 학번
		lblID = new JLabel("학번");
		add(lblID);
		tfID = new JTextField(13); // 13글자 들어가는 사이즈.
		add(tfID);
		
		// 검색
		btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Connection conn=null;
				try {
					//oracle jdbc driver load
					Class.forName("oracle.jdbc.driver.OracleDriver");
					//Connection
					conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","ora_user","hong");
					System.out.println("연결완료");
					
					Statement stmt=conn.createStatement(); // statement 객체 생성
					
					//select
					ResultSet rs=stmt.executeQuery("select * from student where id = '"+tfID.getText()+"'"); //select문 실행 // rs는 cursor역할. 한행씩 while문으로 fetch
					
					// 목록 초기화
					model.setRowCount(0); // 테이블 모델의 행 수를 0으로 설정하여 초기화
					
					if(rs.next()) {	// 검색 결과는 하나니까 while을 if로 바꿔도 됨
						String[] row = new String[4];
						row[0] = rs.getString("id");
						row[1] = rs.getString("name");
						row[2] = rs.getString("dept");
						row[3] = rs.getString("address");
						model.addRow(row);
						
						// 검색 결과가 텍스트필드에 올라가게
						tfID.setText(rs.getString("id"));
						tfName.setText(rs.getString("name"));
						tfDept.setText(rs.getString("dept"));
						tfAddress.setText(rs.getString("address"));
					}
					
					rs.close();
					stmt.close();
					conn.close(); //연결해제
				}catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		add(btnSearch);
		
		
		// 이름 
		lblName = new JLabel("이름");
		add(lblName);
		tfName = new JTextField(20);
		add(tfName);
		
		// 학과
		lblDept = new JLabel("학과");
		add(lblDept);
		tfDept = new JTextField(20);
		add(tfDept);
		
		// 주소
		lblAddress = new JLabel("주소");
		add(lblAddress);
		tfAddress = new JTextField(20);
		add(tfAddress);
		
		
		// 학생 목록 (표 형태)
		String[] colName = {"학번","이름","학과","주소"}; // 열 이름
		model = new DefaultTableModel(colName,0); // (열 이름 들어간 배열 변수명, 행 개수(처음엔 데이터 없으니까 0))
		table = new JTable(model); // model을 테이블과 연동(binding)
		table.setPreferredScrollableViewportSize(new Dimension(250,200)); // 스크롤 생기게 // 테이블의 가로 세로 사이즈 설정 // Dimension() 사용해서 픽셀로 설정
		//add(new JScrollPane(table));
		
		table.addMouseListener(new MouseListener() { // 마우스 이벤트 처리 // 목록에서 클릭했을 때 각각의 값이 tf에 들어가게.

			@Override
			public void mouseClicked(MouseEvent e) { // 마우스가 클릭됐을 때
				table = (JTable)e.getComponent(); // 클릭한 테이블 구하기
				model = (DefaultTableModel)table.getModel(); // 테이블의 모델 구하기
				String id = (String)model.getValueAt(table.getSelectedRow(), 0); // (행 인덱스, 열 인덱스) // getSelectedRow() : 선택한 행의 id 인덱스를 구해줌
				tfID.setText(id);
				String name = (String)model.getValueAt(table.getSelectedRow(), 1); // (행 인덱스, 열 인덱스) // getSelectedRow() : 선택한 행의 name 인덱스를 구해줌
				tfName.setText(name);
				String dept = (String)model.getValueAt(table.getSelectedRow(), 2); // (행 인덱스, 열 인덱스) // getSelectedRow() : 선택한 행의 dept 인덱스를 구해줌
				tfDept.setText(dept);
				String address = (String)model.getValueAt(table.getSelectedRow(), 3); // (행 인덱스, 열 인덱스) // getSelectedRow() : 선택한 행의 address 인덱스를 구해줌
				tfAddress.setText(address);
			}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}
		});		
		
		
		add(new JScrollPane(table)); // 스크롤바 구현
		
		
		
		// 등록 버튼 생성
		btnInsert = new JButton("등록"); // C
		btnInsert.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { // 클릭 이벤트 처리
				Connection conn=null;
				try {
					//oracle jdbc driver load
					Class.forName("oracle.jdbc.driver.OracleDriver");
					//Connection
					conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","ora_user","hong");
					System.out.println("연결완료");
					
					Statement stmt=conn.createStatement(); // statement객체 생성
					
					//insert.
					stmt.executeUpdate("insert into student values('"+tfID.getText()+"','"+tfName.getText()+"','"+tfDept.getText()+"','"+tfAddress.getText()+"')"); 
					
					
					//select
					ResultSet rs=stmt.executeQuery("select * from student");//select문 실행.
					// rs는 cursor역할. 한행씩 while문으로 fetch
					
					
					// 목록 초기화
					model.setRowCount(0); // model의 행의 수를 0으로 설정

					while(rs.next()) {
						String[] row = new String[4];
						row[0] = rs.getString("id");
						row[1] = rs.getString("name");
						row[2] = rs.getString("dept");
						row[3] = rs.getString("address");
						model.addRow(row);
					}
					
					
					rs.close();
					stmt.close();
					conn.close();//연결해제
				}catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		add(btnInsert);
		
///////////////////////////////////////////////////////////////////////////////////////////////////
		
		btnSelect = new JButton("목록"); // R
		btnSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Connection conn=null;
				try {
					//oracle jdbc driver load
					Class.forName("oracle.jdbc.driver.OracleDriver");
					//Connection
					conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","ora_user","hong");
					System.out.println("연결완료");
					
					Statement stmt=conn.createStatement(); // statement객체 생성
					
					//select
					ResultSet rs=stmt.executeQuery("select * from student");//select문 실행.
					// rs는 cursor역할. 한행씩 while문으로 fetch
					
					// 목록 초기화
					model.setRowCount(0); // model의 행의 수를 0으로 설정

					while(rs.next()) {
						String[] row = new String[4];
						row[0] = rs.getString("id");
						row[1] = rs.getString("name");
						row[2] = rs.getString("dept");
						row[3] = rs.getString("address");
						model.addRow(row);
					}
					
					rs.close();
					stmt.close();
					conn.close();//연결해제
				}catch(Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		add(btnSelect);
		
////////////////////////////////////////////////////////////////////////////////////////////////////		
		btnUpdate = new JButton("수정"); // U
		btnUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Connection conn=null;
				try {
					//oracle jdbc driver load
					Class.forName("oracle.jdbc.driver.OracleDriver");
					//Connection
					conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","ora_user","hong");
					System.out.println("연결완료");
					
					Statement stmt=conn.createStatement(); // statement객체 생성
					
					//update. 아이디는 PK라서 변경 불가. 이름이랑 학과만 변경 가능
					stmt.executeUpdate("update student set name='"+tfName.getText()+"', dept='"+tfDept.getText()+"', address='"+tfAddress.getText()+"' where id='"+tfID.getText()+"'");
					
					//select
					ResultSet rs=stmt.executeQuery("select * from student");//select문 실행.
					// rs는 cursor역할. 한행씩 while문으로 fetch
					
					// 목록 초기화
					model.setRowCount(0); // model의 행의 수를 0으로 설정
				
					while(rs.next()) {
						String[] row = new String[4];
						row[0] = rs.getString("id");
						row[1] = rs.getString("name");
						row[2] = rs.getString("dept");
						row[3] = rs.getString("address");
						model.addRow(row);
					}

					
					rs.close();
					stmt.close();
					conn.close();//연결해제
				}catch(Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		add(btnUpdate);
		
//////////////////////////////////////////////////////////////////////////////////////////////////
		btnDelete = new JButton("삭제"); // D
		btnDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "삭제하시겠습니까?", "삭제", JOptionPane.YES_NO_OPTION); // 예(0)/아니오(1) 중 뭘 선택했는지가 정수형태로 리턴됨
				
				if(result==JOptionPane.YES_OPTION) { // 예 눌렀을 때
					Connection conn=null;
					try {
						//oracle jdbc driver load
						Class.forName("oracle.jdbc.driver.OracleDriver");
						//Connection
						conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","ora_user","hong");
						System.out.println("연결완료");
						
						Statement stmt=conn.createStatement(); // statement객체 생성
						
						//delete
						stmt.executeUpdate("delete from student where id='"+tfID.getText()+"'");
						
						//select
						ResultSet rs=stmt.executeQuery("select * from student");//select문 실행.
						// rs는 cursor역할. 한행씩 while문으로 fetch
						
						
						// 입력 항목 초기화
						tfID.setText("");
						tfName.setText("");
						tfDept.setText("");
						tfAddress.setText("");
						
						// 목록 초기화
						model.setRowCount(0); // model의 행의 수를 0으로 설정

						while(rs.next()) {
							String[] row = new String[4];
							row[0] = rs.getString("id");
							row[1] = rs.getString("name");
							row[2] = rs.getString("dept");
							row[3] = rs.getString("address");
							model.addRow(row);
						}

						
						rs.close();
						stmt.close();
						conn.close();//연결해제
					}catch(Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		add(btnDelete);
		
		
		

		
		this.setSize(280,500);
		this.setVisible(true);
	}
	
	
	
//	public static void main(String[] args) {
//		new Student(); // Haksa.java에서 할 거라서 삭제
//
//	}

}
