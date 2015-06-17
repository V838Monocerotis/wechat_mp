package com.goomesoft.wechat.menu;

/**
 * 父按钮
 * @author YXG
 *
 */
public class ParentButton extends Button {

	private Button[] sub_button;

	public Button[] getSub_button() {
		return sub_button;
	}

	public void setSub_button(Button[] sub_button) {
		this.sub_button = sub_button;
	}
	
}
