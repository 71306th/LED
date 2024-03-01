// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class LED extends SubsystemBase {
  
  private AddressableLED m_led;
  private AddressableLEDBuffer m_ledBuffer;

  private int counter = 0;
  private int timeRegulator = 0;
  private int mode = 0;
  private int frequency = 16; // blinks per second
  private int r;
  private int g;
  private int b;

  public LED() {
    m_led = new AddressableLED(Constants.LED.led1Port);
    m_ledBuffer = new AddressableLEDBuffer(Constants.LED.led1Length);
    m_led.setLength(Constants.LED.led1Length);
    for(int i=0;i<m_ledBuffer.getLength();i++) {
      m_ledBuffer.setRGB(i, 0, 0, 0);
    }
    m_led.setData(m_ledBuffer);
    m_led.start();
  }

  public void setMode(int mode) {
    this.mode = mode;
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("mode", mode);
    SmartDashboard.putNumber("timeRegulator", timeRegulator);
    SmartDashboard.putNumber("counter", counter);
    SmartDashboard.putNumber("r", r);
    SmartDashboard.putNumber("g", g);
    SmartDashboard.putNumber("b", b);
    if(timeRegulator%(int)(1/(frequency*0.02)) != 0) {
      timeRegulator++;
    }else{
      switch (mode) {
        case 0: // simple
          for(int i=0;i<m_ledBuffer.getLength();i++) {
            m_ledBuffer.setRGB(i, 255, 97, 0);
          }
          break;
        case 1: // Rainbow Blink
          for(int i=0;i<m_ledBuffer.getLength();i++) {
            if(i%6 == (5+counter)%6)m_ledBuffer.setRGB(i, 255, 0, 255);
            else if (i%6 == (0+counter)%6)m_ledBuffer.setRGB(i, 255, 0, 0);
            else if (i%6 == (1+counter)%6)m_ledBuffer.setRGB(i, 255, 255, 0);
            else if (i%6 == (2+counter)%6)m_ledBuffer.setRGB(i, 0, 255, 0);
            else if (i%6 == (3+counter)%6)m_ledBuffer.setRGB(i, 0, 255, 255);
            else if (i%6 == (4+counter)%6)m_ledBuffer.setRGB(i, 0, 0, 255);
          }
          break;
        case 2: // Rainbow Marquee
          for(int i=0;i<m_ledBuffer.getLength();i++) {
            if(i%m_ledBuffer.getLength() == (5+counter)%m_ledBuffer.getLength())m_ledBuffer.setRGB(i, 255, 0, 255);
            else if (i%m_ledBuffer.getLength() == (0+counter)%m_ledBuffer.getLength())m_ledBuffer.setRGB(i, 255, 0, 0);
            else if (i%m_ledBuffer.getLength() == (1+counter)%m_ledBuffer.getLength())m_ledBuffer.setRGB(i, 255, 255, 0);
            else if (i%m_ledBuffer.getLength() == (2+counter)%m_ledBuffer.getLength())m_ledBuffer.setRGB(i, 0, 255, 0);
            else if (i%m_ledBuffer.getLength() == (3+counter)%m_ledBuffer.getLength())m_ledBuffer.setRGB(i, 0, 255, 255);
            else if (i%m_ledBuffer.getLength() == (4+counter)%m_ledBuffer.getLength())m_ledBuffer.setRGB(i, 0, 0, 255);
            else m_ledBuffer.setRGB(i, 0, 0, 0);
          }
          break;
        case 3: // Red-Blue Blink
          for(int i=0;i<m_ledBuffer.getLength();i++) {
            if(i%2 == (0+counter)%2)m_ledBuffer.setRGB(i, 255, 0, 0);
            else if (i%2 == (1+counter)%2)m_ledBuffer.setRGB(i, 0, 0, 255);
            else m_ledBuffer.setRGB(i, 0, 0, 0);
          }
          break;
        case 4: // Rainbow Cycle
          r = 0;
          g = 0;
          b = 0;
          if(counter%(frequency*6)*2 > (frequency*0)*2 && counter%(frequency*6)*2 <= (frequency*1)*2) b = b-(256 / frequency) < 0 ? 0 : b-(256 / frequency);
          if(counter%(frequency*6)*2 > (frequency*1)*2 && counter%(frequency*6)*2 <= (frequency*2)*2) g += (256 / frequency);
          if(counter%(frequency*6)*2 > (frequency*2)*2 && counter%(frequency*6)*2 <= (frequency*3)*2) r = r-(256 / frequency) < 0 ? 0 : r-(256 / frequency);
          if(counter%(frequency*6)*2 > (frequency*3)*2 && counter%(frequency*6)*2 <= (frequency*4)*2) b += (256 / frequency);
          if(counter%(frequency*6)*2 > (frequency*4)*2 && counter%(frequency*6)*2 <= (frequency*5)*2) g = g-(256 / frequency) < 0 ? 0 : g-(256 / frequency);
          if((counter%(frequency*6)*2 > (frequency*5)*2 && counter%(frequency*6)*2 <= (frequency*6)*2) || counter%(frequency*6)*2 == (frequency*0)*2) r += (256 / frequency);
          for(int i=0;i<m_ledBuffer.getLength();i++) {
            m_ledBuffer.setRGB(i, r, g, b);
          }
          break;
        case 5: // Breathe
          r = 255;
          g = 95;
          if(counter%(frequency*20) > (frequency*0) && counter%(frequency*20) <= (frequency*10)) {
            r = r-(256/frequency) < 0 ? 0 : r-(256/frequency);
            g = g-(96/frequency) < 0 ? 0 : g-(96/frequency);
          }
          if((counter%(frequency*20) > (frequency*10) && counter%(frequency*20) <= (frequency*20)) || counter%(frequency*20) == (frequency*0)) {
            r = r+(256/frequency) > 255 ? 255 : r+(256/frequency);
            g = g+(96/frequency) > 95 ? 96 : g+(96/frequency);
          }
          for(int i=0;i<m_ledBuffer.getLength();i++) {
            m_ledBuffer.setRGB(i, r, g, 0);
          }
          break;
        case 6: // close
          for(int i=0;i<m_ledBuffer.getLength();i++) {
            m_ledBuffer.setRGB(i, 0, 0, 0);
          }
          break;
      }
      m_led.setData(m_ledBuffer);
      counter++;
      timeRegulator++;
    }
  }
}
