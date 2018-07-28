package com.nl.treadmilllauncher.model;

/**
 * Created by Administrator on 2018/1/24 0024.
 */

public class SportData extends CommandData {

    public int data1;
    public int data2;
    public int data3;
    public int data4;
    public int data5;
    public int data6;
    public int data7;
    public int data8;
    public int data9;
    public int data10;

    public SportData(){}

    public SportData(int[] bytes){
        if(bytes == null || bytes.length != 15)
            throw new IllegalArgumentException("data is wrong");

        this.start_code = bytes[0];
        this.command_word = bytes[1];
        this.data = bytes[2];
        this.data1 = bytes[3];
        this.data2 = bytes[4];
        this.data3 = bytes[5];
        this.data4 = bytes[6];
        this.data5 = bytes[7];
        this.data6 = bytes[8];
        this.data7 = bytes[9];
        this.data8 = bytes[10];
        this.data9 = bytes[11];
        this.data10 = bytes[12];
        this.check_code = bytes[13];
        this.terminate_code = bytes[14];
    }

    public int getData1() {
        return data1;
    }

    public void setData1(int data1) {
        this.data1 = data1;
    }

    public int getData2() {
        return data2;
    }

    public void setData2(int data2) {
        this.data2 = data2;
    }

    public int getData3() {
        return data3;
    }

    public void setData3(int data3) {
        this.data3 = data3;
    }

    public int getData4() {
        return data4;
    }

    public void setData4(int data4) {
        this.data4 = data4;
    }

    public int getData5() {
        return data5;
    }

    public void setData5(int data5) {
        this.data5 = data5;
    }

    public int getData6() {
        return data6;
    }

    public void setData6(int data6) {
        this.data6 = data6;
    }

    public int getData7() {
        return data7;
    }

    public void setData7(int data7) {
        this.data7 = data7;
    }

    public int getData8() {
        return data8;
    }

    public void setData8(int data8) {
        this.data8 = data8;
    }

    public int getData9() {
        return data9;
    }

    public void setData9(int data9) {
        this.data9 = data9;
    }

    public int getData10() {
        return data10;
    }

    public void setData10(int data10) {
        this.data10 = data10;
    }

    public float getSportDistance(){
        String distance = Integer.toBinaryString(data6);
        distance += Integer.toBinaryString(data7);
        return Integer.parseInt(distance);
    }

    public int getSportCal(){
        String cal = Integer.toBinaryString(data8);
        cal += Integer.toBinaryString(data9);
        return Integer.parseInt(cal);
    }

    public int getSportDuration(){
        String duration = Integer.toBinaryString(data4 * 10);
        duration += Integer.toBinaryString(data5 * 10);
        return Integer.parseInt(duration);
    }

    public float getSportSpeed(){
        return data2 / 2f;
    }

    public int generateCheckCode(){
        int checkCodeInt = this.command_word + this.data + this.data1+ this.data2 + this.data3
                + this.data4 + this.data5+ this.data6 + this.data7+ this.data8 + this.data9
                + this.data10;
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

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        tmp.append("0x").append(Integer.toString(this.start_code, 16))
                .append("0x").append(Integer.toString(this.command_word, 16))
                .append("0x").append(Integer.toString(this.data, 16))
                .append("0x").append(Integer.toString(this.data1, 16))
                .append("0x").append(Integer.toString(this.data2, 16))
                .append("0x").append(Integer.toString(this.data3, 16))
                .append("0x").append(Integer.toString(this.data4, 16))
                .append("0x").append(Integer.toString(this.data5, 16))
                .append("0x").append(Integer.toString(this.data6, 16))
                .append("0x").append(Integer.toString(this.data7, 16))
                .append("0x").append(Integer.toString(this.data8, 16))
                .append("0x").append(Integer.toString(this.data9, 16))
                .append("0x").append(Integer.toString(this.data10, 16))
                .append("0x").append(Integer.toString(this.check_code, 16))
                .append("0x").append(Integer.toString(this.terminate_code, 16));
        return tmp.toString();
    }

    public byte[] toByteArray(){
        StringBuilder tmp = new StringBuilder();
        tmp.append(Integer.toBinaryString(this.start_code))
                .append(Integer.toBinaryString(this.command_word))
                .append(Integer.toBinaryString(this.data))
                .append(Integer.toBinaryString(this.data1))
                .append(Integer.toBinaryString(this.data2))
                .append(Integer.toBinaryString(this.data3))
                .append(Integer.toBinaryString(this.data4))
                .append(Integer.toBinaryString(this.data5))
                .append(Integer.toBinaryString(this.data6))
                .append(Integer.toBinaryString(this.data7))
                .append(Integer.toBinaryString(this.data8))
                .append(Integer.toBinaryString(this.data9))
                .append(Integer.toBinaryString(this.data10))
                .append(Integer.toBinaryString(this.check_code))
                .append(Integer.toBinaryString(this.terminate_code));
        return tmp.toString().getBytes();
    }
}
