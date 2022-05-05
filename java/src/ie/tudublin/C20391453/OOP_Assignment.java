package ie.tudublin.C20391453;

import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;
import processing.core.PApplet;

public class OOP_Assignment extends PApplet {
    Minim minim;
    AudioPlayer audio;
    AudioInput input;
    AudioBuffer buff;

    float[] lerpedBuff;
    float y = 0;
    float smoothY = 0;
    float smoothAmp = 0;

    FFT fft;

    public void settings() {
        // size(1024, 1024, P3D);
        fullScreen(P3D, SPAN);
    }

    public void setup() {
        minim = new Minim(this);

        audio = minim.loadFile("WARLORDZ (Bailo & ISOxo Remix).mp3", 1024);
        audio.play();
        buff = audio.mix;
        colorMode(HSB);

        fft = new FFT(2048, 44100);

        y = height / 2;
        smoothY = y;

        lerpedBuff = new float[width];
    }

    float off = 0;

    public void draw() {
        background(0);
        float halfH = height / 2;
        float halfW = width / 2;
        float avg = 0;
        float sum = 0;
        off += 1;

        for (int i = 0; i < buff.size(); i++) {
            sum += abs(buff.get(i));
            lerpedBuff[i] = lerp(lerpedBuff[i], buff.get(i), 0.05f);
        }

        avg = sum / (float) buff.size();

        smoothAmp = lerp(smoothAmp, avg, 0.1f);

        float cx = halfW;
        float cy = halfH;
        float angle = 0;

        for (int i = 0; i < audio.bufferSize() - 1; i += 10) {
            float radius = map(smoothAmp, 0, 0.1f, 50, 100);
            angle = map(i, 0, audio.bufferSize(), 0, 2 * PI);
            // stroke(color(map(abs(audio.mix.get(i) * 100), 0, 150, 150, 200), 250, 250));
            float c = map(i, 0, buff.size(), 0, 255);
            stroke(c, 255, 255);

            float lineX = cos(angle) * abs(audio.mix.get(i) * 250);
            float lineY = sin(angle) * abs(audio.mix.get(i) * 250);

            float xPos = cx + (cos(angle) * radius) + lineX;
            float yPos = cy + (sin(angle) * radius) + lineY;

            float lx = cx + (cos(angle) * radius);
            float ly = cy + (sin(angle) * radius);

            strokeWeight(3);
            line(lx, ly, xPos, yPos);
        }

    }

}