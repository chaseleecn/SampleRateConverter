import java.util.ArrayList;
import java.lang.Math;

public class mainTest {
	// 输入音频采样率
	public static long fs;
	
	// 输出音频采样率
	public static long tfs = 44100;
	
    public static void main(String[] args) {
		// 本地音频加载处理
		WaveFileReader wfrAudio = new WaveFileReader("audio.wav");
		int[][] audio = wfrAudio.getData();
		
		// 获取输入音频采样率
		fs = wfrAudio.getSampleRate();
		int len = audio[0].length;
		
		// short数据
		short[] shortAudio = new short[len];
		for(int i = 0; i < len; i++){
			shortAudio[i] = (short)audio[0][i];
		}
		
		// 采样率转换类实例化
		SampleRateConverter src = new SampleRateConverter();
		
		// 采样率转换
		short[] output = src.SRC(shortAudio, fs, tfs);
		
		if(output != null){
			// 保存输出音频
			int[][] outData = new int[1][output.length];
			for(int i = 0; i < output.length; i++){
				outData[0][i] = (int)(output[i]);
			}
			WaveFileWriter wfw = new WaveFileWriter("audioOut-" + tfs + ".wav", outData, tfs);
		}
		
		return;
    }
}