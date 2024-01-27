import java.util.ArrayList;
import java.lang.Math;

/**
* 音频采样率转换器
* @author Chason
* @Time 2016年11月3日
*
*/
public class SampleRateConverter {

	/**
	* 采样率转换
	*
	* @param input		输入音频数组
	*       	
	* @param inputFs	输入音频采样率
	* 
	* @param outputFs	输出音频采样率
	*
	* @return output	输出音频数组，异常返回null
	*/
	public short[] SRC(short[] input, long inputFs, long outputFs){
		// 输入为空检验
		if(input == null){
			System.out.println("Error:\t输入音频为空数组");
			return null;
		}
		
		// 采样率合法检验
		if(inputFs <= 1 || outputFs <= 1){
			System.out.println("Error:\t输入或输出音频采样率不合法");
			return null;
		}
		
		// 输入音频长度
		int len = input.length;
		
		// 输出音频长度
		int outlen = (int)(1.0 * len * outputFs / inputFs);
		
		// 打印信息
		System.out.println("input length = " + len + "\tinput sample rate = " + inputFs);
		System.out.println("output length = " + outlen + "\toutput sample rate = " + outputFs);
		
		double[] S = new double[len];
		double[] T = new double[outlen];
		short[] output = new short[outlen];
		
		// 输入信号归一化
		for(int i = 0; i < len; i++){
			S[i] = input[i] / 32768.0;
		}
		
		// 计算输入输出个数比
		double M = len, N = outlen;
		double F = (M - 1)/(N - 1);
		
		double Fn = 0;
		int Ceil = 0, Floor = 0;
		for(int n = 0; n < outlen; n++){
			
			// 计算输出对应输入的相邻下标
			Fn = F * n;
			Ceil = (int)Math.ceil(Fn);
			Floor = (int)Math.floor(Fn);
			
			// 防止下标溢出
			if(Ceil >= len && Floor < len){
				Ceil = Floor;
			}else if(Ceil >= len && Floor >= len){
				Ceil = len - 1;
				Floor = len - 1;
			}
			
			// 相似三角形法计算输出点近似值
			T[n] = S[Floor] + (Fn - Floor) * (S[Ceil] - S[Floor]);
		}
		
		// 输出信号恢复
		for(int i = 0; i < outlen; i++){
			output[i] = (short)(T[i] * 32768.0);
		}
		return output;
	}
	
}