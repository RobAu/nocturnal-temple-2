package org.audenaerde.sounds;

import org.audenaerde.Main;

import javafx.scene.media.AudioClip;

public class Sounds {
	public static final AudioClip COLLAPSE = new AudioClip(Main.class.getResource("/collapse.wav").toExternalForm());
	public static final AudioClip SPIN = new AudioClip(Main.class.getResource("/spin.wav").toExternalForm());
}
