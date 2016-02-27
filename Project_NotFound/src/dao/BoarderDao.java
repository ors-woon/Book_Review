package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bean.Board;
import bean.Reple;

public class BoarderDao {
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private List list;

	// 현재 시간을 저장 하는 로직
	private String getTimeStamp() {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return f.format(new Date());
	}

	// 댓글 부분 추가
	public void Reple_insertDB(Connection conn, Reple reple) {
		try {
			String sql = "insert into reple (num, id, content, date) values(?, ?, ?, ?);";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reple.getNum());
			pstmt.setString(2, reple.getId());
			pstmt.setString(3, reple.getContext());
			pstmt.setString(4, getTimeStamp());
			pstmt.executeUpdate(); // 쿼리를 실행한다.
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}
	}

	public List<Board> Reple_listDB(Connection conn, String num) {
		try {
			String sql = "SELECT id,date,convert(content using utf8) as content FROM reple where num = ? order by count asc;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, num);
			rs = pstmt.executeQuery(); // 쿼리를 실행한다.
			list = new ArrayList<Reple>();
			while (rs.next()) {
				// subject, writer, contents, reg_date, publication_date,
				// book_img, description
				Reple reple = new Reple();
				reple.setContext(rs.getString("content"));
				reple.setId(rs.getString("id"));
				reple.setDate(rs.getString("date"));
				list.add(reple);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}
		return list;
	}

	// 게시글 관련 정보 넣는 부분
	public void boarder_insertDB(Connection conn, Board board) {
		try {
			String sql = "insert into board (subject, writer, contents, reg_date, bookname, author, publisher, publication_date, book_img, description) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			// auto는 비워놓고 넣음
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getSubject());
			pstmt.setString(2, board.getWriter());
			pstmt.setString(3, board.getContents());
			pstmt.setString(4, getTimeStamp());
			pstmt.setString(5, board.getBookname());
			pstmt.setString(6, board.getAuthor());
			pstmt.setString(7, board.getPublisher());
			pstmt.setString(8, board.getPublication_date());
			pstmt.setString(9, board.getBook_img());
			pstmt.setString(10, board.getDescription());
			pstmt.executeUpdate(); // 쿼리를 실행한다.
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}
	}

	// 글의 수를 가져옴
	public int getBoardCount(Connection conn) {
		int size = 0;
		try {
			String sql = "SELECT count(*) as size FROM board;";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			size = rs.getInt("size");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}
		return size;
	}

	// 검색시에 글 갯수를 가져옴
	public int getBoardCount_search(Connection conn, String keyword, String key) {
		int size = 0;
		try {
			String sql = "SELECT  count(*) as size FROM board where " + keyword + " like '%" + key + "%';";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			size = rs.getInt("size");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}
		return size;
	}

	// 게시글을 list 형태로 담아서 뿌려줌
	public List<Board> boarder_selectDB(Connection conn, int start, int last) {
		try {

			String sql = "SELECT num,subject,bookname,writer FROM board order by num desc limit ?,?;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, last);
			rs = pstmt.executeQuery(); // 쿼리를 실행한다.
			list = new ArrayList<Board>();
			while (rs.next()) {
				// subject, writer, contents, reg_date, publication_date,
				// book_img, description
				Board board = new Board();
				board.setNum(rs.getInt("num"));
				board.setSubject(rs.getString("subject"));
				board.setWriter(rs.getString("writer"));
				board.setBookname(rs.getString("bookname"));
				list.add(board);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}
		return list;
	}

	// 해당하는 글을 클릭시 상세 정보를 담아서 뿌려줄꺼
	public List<Board> boarder_detailDB(Connection conn, String num) {
		try {
			String sql = "SELECT *,convert(description using utf8) as descriptions ,convert(contents using utf8) as content FROM board where num=?;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, num);
			rs = pstmt.executeQuery(); // 쿼리를 실행한다.
			list = new ArrayList<Board>();
			while (rs.next()) {
				// subject, writer, contents, reg_date, publication_date,
				// book_img, description
				Board board = new Board();
				board.setNum(rs.getInt("num"));
				board.setDate(rs.getString("reg_date"));
				board.setSubject(rs.getString("subject"));
				board.setWriter(rs.getString("writer"));
				board.setContents(rs.getString("content"));
				board.setBookname(rs.getString("bookname"));
				board.setAuthor(rs.getString("author"));
				board.setPublisher(rs.getString("publisher"));
				board.setPublication_date(rs.getString("publication_date"));
				board.setBook_img(rs.getString("book_img"));
				board.setDescription(rs.getString("descriptions"));
				list.add(board);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}
		return list;
	}

	// 게시글을 지우는 로직 여기서는 댓글도 함께 지우는게 추가됨 ..
	public void boarder_deletDB(Connection conn, String num) {
		try {
			int num_integer = Integer.parseInt(num);
			String deleteSQL = "DELETE from board where num = ?";
			pstmt = conn.prepareStatement(deleteSQL);
			pstmt.setInt(1, num_integer);
			pstmt.executeUpdate();
			deleteSQL = "DELETE from reple where num = ?";
			pstmt = conn.prepareStatement(deleteSQL);
			pstmt.setInt(1, num_integer);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			System.out.println("board delete문 실행");
		}
	}

	public void boarder_updatDB(Connection conn, Board board, String num) {
		try {

			String sql = " update board set subject= ?, writer= ?, contents=?, reg_date=?, bookname=?, author=?, publisher=?, publication_date=?, book_img=?, description=? where num = ? ;";
			// auto는 비워놓고 넣음
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getSubject());
			pstmt.setString(2, board.getWriter());
			pstmt.setString(3, board.getContents());
			pstmt.setString(4, getTimeStamp());
			pstmt.setString(5, board.getBookname());
			pstmt.setString(6, board.getAuthor());
			pstmt.setString(7, board.getPublisher());
			pstmt.setString(8, board.getPublication_date());
			pstmt.setString(9, board.getBook_img());
			pstmt.setString(10, board.getDescription());
			pstmt.setInt(11, Integer.parseInt(num));
			pstmt.executeUpdate(); // 쿼리를 실행한다.
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}
	}

	public List<Board> boarder_SearchDB(Connection conn, String keyword, String key, int start, int last) {
		try {
			String sql = "SELECT *,convert(description using utf8) as descriptions FROM board where " + keyword
					+ " like '%" + key + "%' order by num desc limit ?,?;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, last);
			rs = pstmt.executeQuery();
			list = new ArrayList<Board>();

			while (rs.next()) {
				// subject, writer, contents, reg_date, publication_date,
				// book_img, description
				Board board = new Board();
				board.setNum(rs.getInt("num"));
				board.setDate(rs.getString("reg_date"));
				board.setSubject(rs.getString("subject"));
				board.setWriter(rs.getString("writer"));
				board.setContents(rs.getString("contents"));
				board.setBookname(rs.getString("bookname"));
				board.setAuthor(rs.getString("author"));
				board.setPublisher(rs.getString("publisher"));
				board.setPublication_date(rs.getString("publication_date"));
				board.setBook_img(rs.getString("book_img"));
				board.setDescription(rs.getString("descriptions"));
				list.add(board);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}
		return list;
	}
}
