package com.github.vedeshkin.cloud.client.controllers;

import com.github.vedeshkin.cloud.client.UIHelper;
import com.github.vedeshkin.cloud.client.network.NetworkService;
import com.github.vedeshkin.cloud.common.messages.AuthorizeRequest;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Created by Vedeshkin on 1/22/2019.
 * All right reserved.
 */
public class LoginController {

    private NetworkService networkService = NetworkService.getInstance();

    @FXML
    PasswordField passwordField;
    @FXML
    TextField loginField;


    public void handleAuth() {
        if (loginField.getText().isEmpty() || passwordField.getText().isEmpty()) {

            UIHelper.showInfoMsgBox("Please enter valid username and password");
            return;
        }

        networkService.send(new AuthorizeRequest(loginField.getText(), passwordField.getText()));
        //UIHelper.changeStage("Cloud Client",this.getClass().getResource("/main.fxml"));

    }


}
