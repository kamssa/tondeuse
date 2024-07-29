package com.example.tondeuse_spring_batch.service;

import com.example.tondeuse_spring_batch.entities.Pelouse;
import com.example.tondeuse_spring_batch.entities.Tondeuse;
import org.springframework.stereotype.Service;

@Service
public class TondeuseService {
    public void executerInstructions(Tondeuse tondeuse, Pelouse pelouse, String instructions) {
        for (char instruction : instructions.toCharArray()) {
            switch (instruction) {
                case 'G':
                    tournerGauche(tondeuse);
                    break;
                case 'D':
                    tournerDroite(tondeuse);
                    break;
                case 'A':
                    avancer(tondeuse, pelouse);
                    break;
            }
        }
    }

    private void tournerGauche(Tondeuse tondeuse) {
        switch (tondeuse.getOrientation()) {
            case 'N':
                tondeuse.setOrientation('W');
                break;
            case 'W':
                tondeuse.setOrientation('S');
                break;
            case 'S':
                tondeuse.setOrientation('E');
                break;
            case 'E':
                tondeuse.setOrientation('N');
                break;
        }
    }

    private void tournerDroite(Tondeuse tondeuse) {
        switch (tondeuse.getOrientation()) {
            case 'N':
                tondeuse.setOrientation('E');
                break;
            case 'E':
                tondeuse.setOrientation('S');
                break;
            case 'S':
                tondeuse.setOrientation('W');
                break;
            case 'W':
                tondeuse.setOrientation('N');
                break;
        }
    }

    private void avancer(Tondeuse tondeuse, Pelouse pelouse) {
        switch (tondeuse.getOrientation()) {
            case 'N':
                if (tondeuse.getY() < pelouse.getMaxY()) {
                    tondeuse.setY(tondeuse.getY() + 1);
                }
                break;
            case 'E':
                if (tondeuse.getX() < pelouse.getMaxX()) {
                    tondeuse.setX(tondeuse.getX() + 1);
                }
                break;
            case 'S':
                if (tondeuse.getY() > 0) {
                    tondeuse.setY(tondeuse.getY() - 1);
                }
                break;
            case 'W':
                if (tondeuse.getX() > 0) {
                    tondeuse.setX(tondeuse.getX() - 1);
                }
                break;
        }
    }
}

