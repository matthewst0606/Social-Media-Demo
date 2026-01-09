package com.fantastic;

import java.util.Scanner;

import com.fantastic.presenter.MainPresenter;
import com.fantastic.service.CommentService;
import com.fantastic.service.PostService;
import com.fantastic.service.UserService;
import com.fantastic.view.MainView;

public class SocialMedia {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        UserService userService = new UserService();
        PostService postService = new PostService();
        CommentService commentService = new CommentService();
        
        
        MainView view = new MainView(input);
        MainPresenter presenter = new MainPresenter(view, userService, postService, commentService);

        presenter.run();
    }
}
