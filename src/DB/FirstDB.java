package DB;

import java.sql.*;
import java.sql.ResultSet;
import java.util.Scanner;

class Data
{
    String a, b, c;
    int d, e, f;

    public String getA() {		return a;	}
    public void setA(String a) {		this.a = a;	} //지점명
    public String getB() {		return b;	}
    public void setB(String b) {		this.b = b;	} //도시
    public String getC() {		return c;	}
    public void setC(String c) {		this.c = c;	} //전화번호
    public int getD() {		return d;	}
    public void setD(int d) {		this.d = d;	}       //종업원수
    public int getE() {		return e;	}
    public void setE(int e) {		this.e = e;	}       //자본금
    public int getF() {		return f;	}
    public void setF(int f) {		this.f = f;	}       //지점개설일
}
class SQLC
{
    private static Connection conn;
    private static PreparedStatement pstmt;

    SQLC() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db"
                ,"root","1234");
    }

    void DataInsert(Data d)
    {
        try {
            // 쿼리문을 세팅하는 작업
            pstmt = conn.prepareStatement(" insert into 대리점 values (?,?,?,?,?,?);");
            pstmt.setString(1, d.getA());       //지점명
            pstmt.setString(2, d.getB());       //도시
            pstmt.setString(3, d.getC());       //전화번호
            pstmt.setInt(4, d.getD());          //종업원수
            pstmt.setInt(5, d.getE());          //자본금
            pstmt.setInt(6, d.getF());          //지점개설일
            // 쿼리문을 실행합니다.
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void selectAll() throws SQLException {
        String sql = "select * from 대리점;";
        pstmt=conn.prepareStatement(sql);
        ResultSet rs =pstmt.executeQuery();
        while(rs.next()){
            System.out.print(rs.getString("지점명")+"/");
            System.out.print(rs.getString("도시")+"/");
            System.out.print(rs.getString("전화번호")+"/");
            System.out.print(rs.getInt("종업원수")+"/");
            System.out.print(rs.getInt("자본금")+"/");
            System.out.print(rs.getDate("지점개설일")+"/");
            System.out.println();
        }
    }

    void findString(String name){

    }
    void BranchSerch(String branch) throws SQLException {
//        String sql = "select * from 대리점 where 지점명='"+branch+"';";
        String sql = "select * from 대리점 where 지점명=?";
        pstmt = SQLC.conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        pstmt.setString(1, branch);

//        while(rs.next()){
//            System.out.print(rs.getString("지점명")+"/");
//            System.out.print(rs.getString("도시")+"/");
//            System.out.print(rs.getString("전화번호")+"/");
//            System.out.print(rs.getInt("종업원수")+"/");
//            System.out.print(rs.getInt("자본금")+"/");
//            System.out.print(rs.getDate("지점개설일")+"/");
//            System.out.println();
//        }
    }

    void DeleteBranch(String branch) throws SQLException {
//        String sql = "delete from 대리점 where 지점명='"+branch+"';";
        String sql = "delete from 대리점 where 지점명= ?;";
//        delete from 대리점 where 지점명='천호점';
        System.out.println(sql);
        pstmt = SQLC.conn.prepareStatement(sql);
        ResultSet rs =pstmt.executeQuery();
        pstmt.setString(1, branch);

    }

}
class InputClass
{
    Data valueReturn() {
        Data d = new Data();

        Scanner scS = new Scanner(System.in);
        Scanner scI = new Scanner(System.in);

        System.out.print("지점을 입력하세요 : ");
        d.setA(scS.nextLine());
        System.out.print("도시를 입력하세요 : ");
        d.setB(scS.nextLine());
        System.out.print("전화번호을 입력하세요 : ");
        d.setC(scS.nextLine());
        System.out.print("종업원수를 입력하세요 : ");
        d.setD(scI.nextInt());
        System.out.print("자본금을 입력하세요 : ");
        d.setE(scI.nextInt());
        System.out.print("지점개설일을 입력하세요 : ");
        d.setF(scI.nextInt());
        return d;

    }

}

public class FirstDB {
    public static void main(String[] args) throws SQLException {
        // TODO Auto-generated method stub
        Scanner sc = new Scanner(System.in);

        SQLC sq = new SQLC();
        InputClass ic = new InputClass();
        while(true)
        {
            System.out.print("1. 입력 2.전체출력 3.지점명 검색 4.지점 삭제 5.종료 : ");
            int num = sc.nextInt();
            if(num==1)
            {
                sq.DataInsert(ic.valueReturn()); // Data타입의 d를 DataInsert함수에 넣어 쿼리문 만들어 던져
            }
            else if(num == 2){
                sq.selectAll();
            }
            else if(num == 3){
                System.out.print("검색할 지점명을 입력하세요 : ");
                String branch = sc.next();
                sq.BranchSerch(branch);

            }
            else if(num == 4){
                System.out.print("삭제할 지점명을 입력하세요 : ");
                String branch = sc.next();
                sq.DeleteBranch(branch);

            }
            else if(num == 5){
                System.out.println("프로그램을 종료합니다.");
                break;
            }
            else{
                System.out.println("잘못 입력하셨습니다.");
            }
        }
    }

}
