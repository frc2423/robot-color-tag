package frc.robot.subsystems.vision;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import edu.wpi.first.math.geometry.Rotation2d;

public class VisionCommands {
        private VisionSubsystem vision;
        private SwerveSubsystem drivebase;

        public VisionCommands(VisionSubsystem vision, SwerveSubsystem drivebase) {
                this.vision = vision;
                this.drivebase = drivebase;
        }

        public Command notePoseAutoAlign() {
                Command command = Commands.run(() -> drivebase.actuallyLookAngle(vision.getTurn()))
                                .until(() -> vision.isAlignedNote());
                command.setName("note auto rotate align pose");
                return command;
        }

        public Command noteAutoAlign() {
                Command turn = Commands.run(() -> drivebase.turn(-vision.getNoteYaw() * .13), drivebase, vision);
                Command command = turn.until(() -> vision.isAlignedNote());
                command.setName("note auto rotate align");
                return command;
        }

        public Command noteAutoAlignPickUp() {
                Command turn = Commands.run(() -> drivebase.turn(-vision.getNoteYaw() * .075), drivebase, vision);
                Command driveAndTurn = Commands.run(() -> drivebase.turnAndGo(2, vision.yawAfterAligned()), drivebase,
                                vision);
                Command stopMoving = Commands.runOnce(() -> drivebase.turnAndGo(0, 0), drivebase);
                Command command = Commands.sequence(

                                turn.until(() -> vision.isAlignedNote()),

                                stopMoving);
                return command;
        }

        public Command noteAutoAlignPickUpHandoffAndPrepareToShoot() {
                Command turn = Commands.run(() -> drivebase.turn(-vision.getNoteYaw() * .075), drivebase, vision);
                Command driveAndTurn = Commands.run(() -> drivebase.turnAndGo(2, vision.yawAfterAligned()), drivebase,
                                vision);
                Command drive = drivebase.getTeleopDriveCommand();
                Command drive1 = drivebase.getTeleopDriveCommand();

                Command command = Commands.run(() -> drivebase.turn(2));
                return command;
        }
}
