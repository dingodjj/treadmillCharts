package com.nl.treadmilllauncher.model;

/**
 * Created by Administrator on 2018/1/24 0024.
 */

public class CommandData {

    public static final int FLAG = 0xff;

    //开关机命令字
    public static final int OPEN_OR_CLOSE_COMMAND_WORD = 0xA1;
    //关机数据
    public static final int OPEN_DATA = 0x00;
    //开机数据
    public static final int CLOSE_DATA = 0x11;
    //调节速度指令
    public static final int SPEED_CONTROL_COMMAND_WORD = 0xA2;
    //调节坡度指令
    public static final int SLOPE_CONTROL_COMMAND_WORD = 0xA3;
    //显示心率指令
    public static final int HEARTRATE_DISPLAY_COMMAND_WORD = 0xA4;
    //显示跑步机ID
    public static final int TREADMILL_ID_COMMAND_WORD = 0xA5;
    //读取运动数据
    public static final int SPORT_DATA_COMMAND_WORD = 0xA6;

    //起始位
    public static final int START_CODE = 0xAA;
    //终止位
    public static final int TERMINATE_CODE = 0x55;

    public int start_code = START_CODE;
    public int command_word;
    public int data;
    public int check_code;
    public int terminate_code = TERMINATE_CODE;

    public CommandData(){
    }

    public CommandData(int[] bytes){
        if(bytes == null || bytes.length != 5)
            throw new IllegalArgumentException("data is wrong");

        this.start_code = bytes[0];
        this.command_word = bytes[1];
        this.data = bytes[2];
        this.check_code = bytes[3];
        this.terminate_code = bytes[4];
    }

    public int getStart_code() {
        return start_code;
    }

    public void setStart_code(int start_code) {
        this.start_code = start_code;
    }

    public int getCommand_word() {
        return command_word;
    }

    public void setCommand_word(int command_word) {
        this.command_word = command_word;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public int getCheck_code() {
        return check_code;
    }

    public void setCheck_code(int check_code) {
        this.check_code = check_code;
    }

    public int getTerminate_code() {
        return terminate_code;
    }

    public void setTerminate_code(int terminate_code) {
        this.terminate_code = terminate_code;
    }

    public void setTerminate_code(byte terminate_code) {
        this.terminate_code = terminate_code;
    }

    public static final byte intToByte(int x) {
        return (byte) x;
    }

    public static final int byteToInt(byte b) {
        //Java 总是把 byte 当做有符处理；我们可以通过将其和 0xFF 进行二进制与得到它的无符值
        return b & 0xFF;
    }

    public int generateCheckCode(){
        int checkCodeInt = this.command_word + this.data;
        String result = Integer.toBinaryString(checkCodeInt);
        char[] resChars = new char[]{'0', '0', '0', '0', '0', '0', '0', '0'};
        int index = 7;
        for (int i = result.length() - 1; i >= 0 ; i--) {
            if(index < 0) break;
            resChars[index--] = result.charAt(i);
        }
        result = new String(resChars);
        return Integer.parseInt(result, 2);
    }

    public boolean isCheckCodeVaild(CommandData data){
        if (data == null)
            throw new IllegalArgumentException("arguement is null");
        return data.check_code == generateCheckCode();
    }

    public static final byte binaryToByte(String binaryStr){
        return Byte.parseByte(binaryStr);
    }

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        tmp.append("0x").append(Integer.toString(this.start_code, 16))
                .append("0x").append(Integer.toString(this.command_word, 16))
                .append("0x").append(Integer.toString(this.data, 16))
                .append("0x").append(Integer.toString(this.check_code, 16))
                .append("0x").append(Integer.toString(this.terminate_code, 16));
        return tmp.toString();
    }

    public byte[] toByteArray(){
        StringBuilder tmp = new StringBuilder();
        tmp.append(Integer.toBinaryString(this.start_code))
                .append(Integer.toBinaryString(this.command_word))
                .append(Integer.toBinaryString(this.data))
                .append(Integer.toBinaryString(this.check_code))
                .append(Integer.toBinaryString(this.terminate_code));
        return tmp.toString().getBytes();
    }
}
