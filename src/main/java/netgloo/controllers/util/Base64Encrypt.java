package netgloo.controllers.util;
public class Base64Encrypt {
    private static final String CODES="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

    /**
     * 加密方法
     * @param in 输入字符串的数组
     * @return
     */
    public static String base64Encode( byte[] in){

        int length=in.length/3;
        int temp = in.length%3;

        if(temp>0){
            length=length+1;
        }
        StringBuilder out = new StringBuilder(length*4);//初始化加密后的数据长度


        int b;
        for(int i=0;i<in.length;i+=3){
            b = (in[i]&0xFC)>>2;
            out.append(CODES.charAt(b));
            b =(in[i]&0x03)<<4;
            if(i+1<in.length){
                b|=(in[i+1]&0xF0)>>4;
                out.append(CODES.charAt(b));
                b=(in[i+1]&0x0F)<<2;
                if(i+2<in.length){
                    b|=(in[i+2]&0xC0)>>6;
                    out.append(CODES.charAt(b));
                    b = in[i+2]&0x3F;
                    out.append(CODES.charAt(b));
                }else{
                    out.append(CODES.charAt(b));
                    out.append('=');
                }
            }else{
                out.append(CODES.charAt(b));
                out.append("==");
            }
        }
        return out.toString();
    }

    /**
     * 解密方法
     * @param input 加密后的字符串
     * @return   解密后的字节数组
     */
    private static byte [] base64Decode(String input){
        if(input.length() %4  !=0){
            throw new  IllegalArgumentException("Invalid base64 input");
        }

        byte decoded[] = new byte [((input.length()*3)/4)-(input.indexOf('=')>0?(input.length()-input.indexOf('=')):0)];//原数组的长度
        char[] inChars = input.toCharArray();
        int j=0;
        int b[] = new int [4];
        for(int i=0;i<inChars.length;i+=4){
            b[0]=CODES.indexOf(inChars[i]);
            b[1]=CODES.indexOf(inChars[i+1]);
            b[2]=CODES.indexOf(inChars[i+2]);
            b[3]=CODES.indexOf(inChars[i+3]);
            decoded[j++]=(byte)((b[0]<<2)|(b[1]>>4));
            if(b[2]<64){
                decoded[j++]=(byte)((b[1]<<4)|(b[2]>>2));
                if(b[3]<64){
                    decoded[j++]=(byte)((b[2]<<6)|b[3]);
                }
            }

        }
        return decoded;
    }
    public static void main(String[] args) {
        String input = "82793351";
        String encode = base64Encode(input.getBytes());
        System.out.println("encode: "+encode);
        String decode = new String(base64Decode(encode));
        System.out.println("decode: "+decode);
    }

}
