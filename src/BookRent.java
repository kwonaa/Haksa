// 0703
// 학생이 책을 대출한다.
// 콤보박스
// 클릭 이벤트 처리
// 동적 쿼리
// 중복 코드 메서드로 만들기

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class BookRent extends JPanel{
	DefaultTableModel model=null;
	JTable table=null;
	
	String query=null;
	
	Connection con=null;
	Statement stmt=null;
	ResultSet rs = null;  // select결과를 fetch하는 객체
	
	
	public BookRent() {
		query="select student.id, student.name, book.title, rentbook.rdate"
				+ " from student,rentbook,book"
				+ " where student.id=rentbook.id"
				+ " and rentbook.bid=book.bid order by student.id";
		
		
//		this.setTitle("책대출현황");
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setLayout(null); // layout 사용 안 함. 컴포넌트의 위치, 크기 직접 설정
		
		JLabel lblDepartment=new JLabel("학과");
		lblDepartment.setBounds(10, 10, 30, 20); // setBounds(x 좌표, y 좌표, 가로 사이즈, 세로 사이즈) : 위치를 정할 수 있음
		this.add(lblDepartment);
		
		// 콤보박스
		String[] dept={"전체","컴퓨터시스템","멀티미디어","컴퓨터공학"}; // 콤보박스에 넣을 목록을 스트링 배열로 만듦
		JComboBox cbDept=new JComboBox(dept);
		cbDept.addActionListener(new ActionListener() { // 클릭했을 때
			@Override
			public void actionPerformed(ActionEvent e) {
				//기본쿼리
				query ="select student.id,student.name,book.title,rentbook.rdate"
						+ " from student,book,rentbook"
						+ " where student.id = rentbook.id"
						+ " and book.bid = rentbook.bid";
				JComboBox cb=(JComboBox)e.getSource(); // 이벤트가 발생한 컴포넌트를 찾아와서 콤보박스로 변환.
				
				if(cb.getSelectedIndex()==0) { //전체 // getSelectedIndex() : 선택한 인덱스를 구해줌
					query+=" order by student.id"; // 기본 쿼리에 추가 // 동적 쿼리 : 응용프로그램이 실행되는 과정(런타임)에서 쿼리문이 달라짐
				}else if(cb.getSelectedIndex()==1) { //컴퓨터시스템
					query+=" and student.dept='컴퓨터시스템' order by student.id";
				}else if(cb.getSelectedIndex()==2) { //멀티미디어
					query+=" and student.dept='멀티미디어' order by student.id";
				}else if(cb.getSelectedIndex()==3) { //컴퓨터공학
					query+=" and student.dept='컴퓨터공학' order by student.id";
				}	
				System.out.println(query); // 디버깅을 위해 작성
				
				list(); //목록출력 // 리스트 메서드로 예외처리 따로 뺀 것임. 아래 있음. 
				
			}});
		
		cbDept.setBounds(45, 10, 100, 20);
		this.add(cbDept);
		
		// 테이블
		String colName[]={"학번","이름","도서명","대출일"};
		model=new DefaultTableModel(colName,0);
		table=new JTable(model);
		table.setPreferredScrollableViewportSize(new Dimension(470,200));
//		this.add(table);
		JScrollPane sp=new JScrollPane(table);
		sp.setBounds(10, 40, 460, 250); // 크기 설정
		this.add(sp);
		
		//전체
		list();
		
		this.setSize(490, 400);
		this.setVisible(true);
	}
	
	//select 실행 
	public void list() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");//oracle driver 로드
			// oracle xe연결. enterprise는 xe대신 orcl
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","ora_user","hong");
			// statement객체 생성.
			stmt=con.createStatement();			
			rs=stmt.executeQuery(query);
			//목록 초기화
			model.setRowCount(0); // medel의 행의 수를 0으로 변경
			
			// fetch. 한행씩 읽어오기
			while(rs.next()) {
				String[] row=new String[4];
				row[0]=rs.getString("id");
				row[1]=rs.getString("name");
				row[2]=rs.getString("title");
				row[3]=rs.getString("rdate");
				model.addRow(row);
			}
		}catch(Exception e1) {
			e1.printStackTrace();
		}finally { // 마지막에 실행해야 하는 것들(close)을 finally에 넣어주면 좋음
			try {
				if(rs!=null) {rs.close();} // null이 아니면 닫아라
				if(stmt!=null) {stmt.close();}
				if(con!=null) {con.close();}
			}catch(Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	

//	public static void main(String[] args) {
//		new BookRent(); // Haksa.java에서 할 거라서 삭제
//	}

}
