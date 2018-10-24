package syoribuShooting;

import java.sql.*;

public class Ranking {
    /*
        データベースにユーザー情報を登録する
        第1引数 : 得点
        第2引数 : ユーザー名
        戻り値  : なし
     */
    public static void InsertData(int points, String username) throws Exception {
        Connection con = null;
        Statement stmt = null;

        /*
            接続先ホスト   : cricket          <= IP or ホスト名
            ポート番号     : 5432             <= PostgreSQLは標準で5432番を使用する
            データベース名 : hamako-festival  <= 使用するDB名
            接続ユーザー名 : www              <= DB作成ユーザー
            接続パスワード : passwd           <= DB作成ユーザーのパスワード
        */
        String host   = "cricket.hamako-ths.ed.jp";
        int    port   = 5432;
        String dbname = "hamako-festival";
        String url    = "jdbc:postgresql://" + host + ":" + port + "/" + dbname;
        String user   = "www";
        String pass   = "passwd";

        try {
            // クラスのロード
            Class.forName("org.postgresql.Driver");

            // PostgreSQL へ接続(JDBCを使用)
            con = DriverManager.getConnection( url, user, pass );

            // 自動コミットOFF => トランザクション開始
            con.setAutoCommit( false );

            // INSERT文の実行
            stmt = con.createStatement();
            String sql;
            sql = "INSERT INTO \"Ranking\" ( \"Points\", \"UserName\", \"Date\" ) VALUES ('" + points + "','" + username + "', CURRENT_TIMESTAMP )";
            stmt.executeUpdate( sql );
            con.commit();

        } catch ( Exception e ) {
            con.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            // 接続解除
            // エラー処理もこ↑こ↓でする
            if ( stmt != null ) stmt.close();
            if ( con  != null ) con.close();
        }

    }


    public static int GetRanking(int points) throws Exception{
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        int rank = -1;

        /*
            接続先ホスト   : cricket          <= IP or ホスト名
            ポート番号     : 5432             <= PostgreSQLは標準で5432番を使用する
            データベース名 : hamako-festival  <= 使用するDB名
            接続ユーザー名 : www              <= DB作成ユーザー
            接続パスワード : passwd           <= DB作成ユーザーのパスワード
        */
        String host   = "cricket.hamako-ths.ed.jp";
        int    port   = 5432;
        String dbname = "hamako-festival";
        String url    = "jdbc:postgresql://" + host + ":" + port + "/" + dbname;
        String user   = "www";
        String pass   = "passwd";

        try {
            // クラスのロード
            Class.forName("org.postgresql.Driver");

            // PostgreSQL へ接続(JDBCを使用)
            con = DriverManager.getConnection( url, user, pass );

            // 自動コミットOFF => トランザクション開始
            con.setAutoCommit( false );

            // SELECT文の実行
            stmt = con.createStatement();
            String sql;

            sql = "SELECT \"Points\" FROM \"Ranking\" ORDER BY \"Points\" DESC";
            rs = stmt.executeQuery(sql);

            //得点がどの位置にあるかを探す
            rank = 1;
            while(rs.next()){
              int code = rs.getInt("Points");
              if(code == points){
                break;
              }
              rank++;
            }

            con.commit();

        } catch ( Exception e ) {
            con.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            // 接続解除
            // エラー処理もこ↑こ↓でする
            if ( stmt != null ) stmt.close();
            if ( con  != null ) con.close();
            rs.close();
        }
        return rank;

    }
}
