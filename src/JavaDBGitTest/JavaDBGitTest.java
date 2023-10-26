package JavaDBGitTest;


import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

class Data {
    String name, telnum, address;

    public String getName() {return name;}
    public void setName(String name) {this.name = name;} //이름
    public String getTelnum() {return telnum;}
    public void setTelnum(String telnum) {this.telnum = telnum;} //전화번호
    public String getAddr() {return address;}
    public void setAddr(String address) {this.address = address;} //주소

}

class InputClass
{
    Data valueReturn() {
        Data d = new Data();

        Scanner scS = new Scanner(System.in);
        System.out.print("이름을 입력하세요 : ");
        d.setName(scS.nextLine());
        System.out.print("전화번호을 입력하세요 : ");
        d.setTelnum(scS.nextLine());
        System.out.print("주소를 입력하세요 : ");
        d.setAddr(scS.nextLine());
        return d;
    }

}
class SQLC {
    private static Connection conn;
    private static PreparedStatement pstmt;

    SQLC() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javadbgittest"
                , "root", "1234");
    }

    void DataInsert(Data d) {
        try {
            // 쿼리문을 세팅하는 작업
            pstmt = conn.prepareStatement(" insert into phone values (?,?,?);");
            pstmt.setString(1, d.getName());       //이름
            pstmt.setString(2, d.getTelnum());       //전화번호
            pstmt.setString(3, d.getAddr());       //주소

            // 쿼리문을 실행합니다.
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void selectAll() throws SQLException {
        String sql = "select * from phone;";
        pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            System.out.print(rs.getString("name") + "/");
            System.out.print(rs.getString("phonenumber") + "/");
            System.out.print(rs.getString("address") + "/");
            System.out.println();
        }
    }


    void BranchSerch(String branch) throws SQLException {
        String sql = "select * from phone where name=?;";
        pstmt = SQLC.conn.prepareStatement(sql);
        pstmt.setString(1, branch);
        ResultSet rs = pstmt.executeQuery();
        String checkName="";

        while (rs.next()) {
            checkName += (rs.getString("name") + "/");
            checkName += (rs.getString("phonenumber") + "/");
            checkName += (rs.getString("address") + "/");
        }

        if(checkName.isEmpty()){
            System.out.println("전화번호부에 없습니다.");
        }
        else{
            System.out.println(checkName);
        }
    }
    void DeleteBranch(String branch) throws SQLException {
        String sql = "delete from phone where name= ?;";
        pstmt = SQLC.conn.prepareStatement(sql);
        pstmt.setString(1, branch);
        if(pstmt.executeUpdate()==1){               //전화번호부에 있는지 확인 & 있으면 삭제
            System.out.println("전화번호부에서 삭제했습니다.");
        }
        else{                                       //없으면 출력
            System.out.println("전화번호부에 없습니다.");
        }
    }
}

public class JavaDBGitTest {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);

        SQLC sq = new SQLC();
        InputClass ic = new InputClass();
        while (true) {
            System.out.print("1. 입력 2.전체출력 3.이름 검색 4.이름 삭제 5.종료 : ");
            int num = sc.nextInt();
            if (num == 1) {
                sq.DataInsert(ic.valueReturn());
            }
            else if (num == 2) {
                sq.selectAll();
            }
            else if (num == 3) {
                System.out.print("검색할 이름을 입력하세요 : ");
                String branch = sc.next();
                sq.BranchSerch(branch);

            }
            else if (num == 4) {
                System.out.print("삭제할 이름을 입력하세요 : ");
                String branch = sc.next();
                sq.DeleteBranch(branch);

            }
            else if (num == 5) {
                System.out.println("프로그램을 종료합니다.");
                break;
            }
            else {
                System.out.println("잘못 입력하셨습니다.");
            }
        }
    }
}

