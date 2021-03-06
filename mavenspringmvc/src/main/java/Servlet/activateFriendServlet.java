package Servlet;

import Dao.*;
import Entity.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

import tools.webSocket;
@WebServlet(name = "activateFriendServlet")
public class activateFriendServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.valueOf(request.getParameter("userId").toString());
        int friendId = Integer.valueOf(request.getParameter("friendId").toString());
        String agree = request.getParameter("agree");

        int fsid1 = UnswBookFriendshipDAO.getFriendshipByTwoId(userId,friendId);
        int fsid2 = UnswBookFriendshipDAO.getFriendshipByTwoId(friendId,userId);
        UnswBookFriendshipEntity fs1 = UnswBookFriendshipDAO.retrieve(fsid1);
        UnswBookFriendshipEntity fs2 = UnswBookFriendshipDAO.retrieve(fsid2);
        if(agree.equals("1")){
            fs1.setStatus("1");
            fs2.setStatus("1");
            UnswBookFriendshipDAO.saveOrUpdate(fs1);
            UnswBookFriendshipDAO.saveOrUpdate(fs2);

            Timestamp current_time = new Timestamp(System.currentTimeMillis());
            UnswBookUserEntity friend = UnswBookUserDAO.retrieve(friendId);
            UnswBookNotificationEntity notification = new UnswBookNotificationEntity();
            notification.setUid(userId);
            notification.setTitle("Friend request accepted");
            notification.setText(friend.getName()+" accept your friend request :)  ");
            notification.setTime(current_time);
            UnswBookNotificationDAO.saveOrUpdate(notification);

            UnswBookActivityEntity activity = new UnswBookActivityEntity();
            activity.setActivity("accept friend request from "+userId);
            activity.setTime(current_time);
            activity.setUserId(friendId);
            UnswBookActivityDAO.saveOrUpdate(activity);//friend activity

            UnswBookTripleEntity triple1 = new UnswBookTripleEntity();
            UnswBookTripleEntity triple2 = new UnswBookTripleEntity();

            triple1.setNodeFrom("P"+userId);
            triple1.setEdge("E1");
            triple1.setNodeTo("P"+friendId);

            triple2.setNodeFrom("P"+friendId);
            triple2.setEdge("E1");
            triple2.setNodeTo("P"+userId);

            UnswBookTripleDAO.saveOrUpdate(triple1);
            UnswBookTripleDAO.saveOrUpdate(triple2);


        }else{
            UnswBookFriendshipDAO.delete(fs1);
            UnswBookFriendshipDAO.delete(fs2);

            Timestamp current_time = new Timestamp(System.currentTimeMillis());
            UnswBookUserEntity friend = UnswBookUserDAO.retrieve(friendId);
            UnswBookNotificationEntity notification = new UnswBookNotificationEntity();
            notification.setUid(userId);
            notification.setTitle("Friend request rejected");
            notification.setText(friend.getName()+" reject your friend request :( ");
            notification.setTime(current_time);
            UnswBookNotificationDAO.saveOrUpdate(notification);

            UnswBookActivityEntity activity = new UnswBookActivityEntity();
            activity.setActivity("reject friend request from "+userId);
            activity.setTime(current_time);
            activity.setUserId(friendId);
            UnswBookActivityDAO.saveOrUpdate(activity);
        }



        request.getSession().setAttribute("currentUserId",userId);
        request.getRequestDispatcher("mainpage.jsp").forward(request,response);

    }
}
