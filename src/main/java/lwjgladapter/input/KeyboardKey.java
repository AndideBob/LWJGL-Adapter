package lwjgladapter.input;

import static org.lwjgl.glfw.GLFW.*;

public enum KeyboardKey {
	NONE(Integer.MIN_VALUE),
	KEY_0(GLFW_KEY_0),
	KEY_1(GLFW_KEY_1),
	KEY_2(GLFW_KEY_2),
	KEY_3(GLFW_KEY_3),
	KEY_4(GLFW_KEY_4),
	KEY_5(GLFW_KEY_5),
	KEY_6(GLFW_KEY_6),
	KEY_7(GLFW_KEY_7),
	KEY_8(GLFW_KEY_8),
	KEY_9(GLFW_KEY_9),
	KEY_A(GLFW_KEY_A),
	KEY_B(GLFW_KEY_B),
	KEY_C(GLFW_KEY_C),
	KEY_D(GLFW_KEY_D),
	KEY_E(GLFW_KEY_E),
	KEY_F(GLFW_KEY_F),
	KEY_G(GLFW_KEY_G),
	KEY_H(GLFW_KEY_H),
	KEY_I(GLFW_KEY_I),
	KEY_J(GLFW_KEY_J),
	KEY_K(GLFW_KEY_K),
	KEY_L(GLFW_KEY_L),
	KEY_M(GLFW_KEY_M),
	KEY_N(GLFW_KEY_N),
	KEY_O(GLFW_KEY_O),
	KEY_P(GLFW_KEY_P),
	KEY_Q(GLFW_KEY_Q),
	KEY_R(GLFW_KEY_R),
	KEY_S(GLFW_KEY_S),
	KEY_T(GLFW_KEY_T),
	KEY_U(GLFW_KEY_U),
	KEY_V(GLFW_KEY_V),
	KEY_W(GLFW_KEY_W),
	KEY_X(GLFW_KEY_X),
	KEY_Y(GLFW_KEY_Y),
	KEY_Z(GLFW_KEY_Z),
	KEY_NUM0(GLFW_KEY_KP_0),
	KEY_NUM1(GLFW_KEY_KP_1),
	KEY_NUM2(GLFW_KEY_KP_2),
	KEY_NUM3(GLFW_KEY_KP_3),
	KEY_NUM4(GLFW_KEY_KP_4),
	KEY_NUM5(GLFW_KEY_KP_5),
	KEY_NUM6(GLFW_KEY_KP_6),
	KEY_NUM7(GLFW_KEY_KP_7),
	KEY_NUM8(GLFW_KEY_KP_8),
	KEY_NUM9(GLFW_KEY_KP_9),
	KEY_ESCAPE(GLFW_KEY_ESCAPE),
	KEY_SPACE(GLFW_KEY_SPACE),
	KEY_ENTER(GLFW_KEY_ENTER),
	KEY_BACKSPACE(GLFW_KEY_BACKSPACE),
	KEY_TAB(GLFW_KEY_TAB),
	KEY_ALT_LEFT(GLFW_KEY_LEFT_ALT),
	KEY_ALT_RIGHT(GLFW_KEY_RIGHT_ALT),
	KEY_CRTL_LEFT(GLFW_KEY_LEFT_CONTROL),
	KEY_CRTL_RIGHT(GLFW_KEY_RIGHT_CONTROL),
	KEY_SHIFT_LEFT(GLFW_KEY_LEFT_SHIFT),
	KEY_SHIFT_RIGHT(GLFW_KEY_RIGHT_SHIFT),
	KEY_ARROW_UP(GLFW_KEY_UP),
	KEY_ARROW_LEFT(GLFW_KEY_LEFT),
	KEY_ARROW_RIGHT(GLFW_KEY_RIGHT),
	KEY_ARROW_DOWN(GLFW_KEY_DOWN),
	KEY_DELETE(GLFW_KEY_DELETE),
	KEY_END(GLFW_KEY_END),
	KEY_F1(GLFW_KEY_F1),
	KEY_F2(GLFW_KEY_F2),
	KEY_F3(GLFW_KEY_F3),
	KEY_F4(GLFW_KEY_F4),
	KEY_F5(GLFW_KEY_F5),
	KEY_F6(GLFW_KEY_F6),
	KEY_F7(GLFW_KEY_F7),
	KEY_F8(GLFW_KEY_F8),
	KEY_F9(GLFW_KEY_F9),
	KEY_F10(GLFW_KEY_F10),
	KEY_F11(GLFW_KEY_F11),
	KEY_F12(GLFW_KEY_F12);
	
	private int glfwValue;
	
	private KeyboardKey(int glfwValue){
		this.glfwValue = glfwValue;
	}
	
	public static KeyboardKey fromGLFWButton(int key){
		for(KeyboardKey button : values()){
			if(button.glfwValue == key){
				return button;
			}
		}
		return NONE;
	}
	
	public static final KeyboardKey[] letterButtons = {KEY_A, KEY_B, KEY_C, KEY_D, KEY_E, KEY_F, KEY_G, KEY_H, KEY_I, KEY_J,
			KEY_K, KEY_L, KEY_M, KEY_N, KEY_O, KEY_P, KEY_Q, KEY_R, KEY_S, KEY_T, KEY_U, KEY_V, KEY_W, KEY_X, KEY_Y, KEY_Z};
	
	public static final KeyboardKey[] numberButtons = {KEY_0, KEY_1, KEY_2, KEY_3, KEY_4, KEY_5, KEY_6, KEY_7, KEY_8, KEY_9};
	
	public static final KeyboardKey[] numPadButtons = {KEY_NUM0, KEY_NUM1, KEY_NUM2, KEY_NUM3, KEY_NUM4,
			KEY_NUM5, KEY_NUM6, KEY_NUM7, KEY_NUM8, KEY_NUM9};
	
	public static final KeyboardKey[] functionButtons = {KEY_F1, KEY_F2, KEY_F3, KEY_F4, KEY_F5, KEY_F6, KEY_F7, KEY_F8, KEY_F9, KEY_F10, KEY_F11, KEY_F12};
}
