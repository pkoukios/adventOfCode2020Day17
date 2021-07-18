import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws Exception {
        String data = new String(Files.readAllBytes(Paths.get("C:\\Users\\pkoukios\\OneDrive - SWORD SA\\Documents\\adventOfCode17.txt")));
        String[] lines = data.split("\n");

//        Arrays.stream(lines)
//                .forEach(System.out::println);

        int xMin = -1;
        int xMax = lines[0].length() - 1;
        int yMin = -1;
        int yMax = lines.length;
        int zMin = -1;
        int zMax = 1;
        int wMin = -1;
        int wMax = 1;

        Set<Position> activeCubes = new HashSet<>();

        for (int y = 0; y < yMax; y++) {
            for (int x = 0; x < xMax; x++) {
                if (lines[y].charAt(x) == '#') {
                    activeCubes.add(new Position(x, y, 0, 0));
                }
            }
        }

//        activeCubes.stream()
//                .sorted(comparing(Position::getY)
//                        .thenComparing(Position::getX))
//                .forEach(System.out::println);

        for (int cycle = 0; cycle < 6; cycle++) {
            Set<Position> updatedActiveCubes = new HashSet<>();

            for (int x = xMin; x <= xMax; x++) {
                for (int y = yMin; y <= yMax; y++) {
                    for (int z = zMin; z <= zMax; z++) {
                        for (int w = wMin; w <= wMax; w++) {
                            int activeNeighbors = countActiveNeighbors(activeCubes, x, y, z, w);
                            Position cubePosition = new Position(x, y, z, w);
                            if (activeCubes.contains(cubePosition) ?
                                    activeNeighbors == 2 || activeNeighbors == 3 :
                                    activeNeighbors == 3) {
                                updatedActiveCubes.add(cubePosition);
                                xMin = Math.min(xMin, x - 1);
                                xMax = Math.max(xMax, x + 1);
                                yMin = Math.min(yMin, y - 1);
                                yMax = Math.max(yMax, y + 1);
                                zMin = Math.min(zMin, z - 1);
                                zMax = Math.max(zMax, z + 1);
                                wMin = Math.min(wMin, w - 1);
                                wMax = Math.max(wMax, w + 1);
                            }
                        }
                    }
                }
            }
            activeCubes = updatedActiveCubes;
        }
        System.out.println(activeCubes.size());
    }

    private static int countActiveNeighbors(Set<Position> activeCubes, int x, int y, int z, int w) {
        int count = 0;

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    for (int dw = -1; dw <= 1; dw++) {
                        if (dx == 0 && dy == 0 && dz == 0 && dw == 0) {
                            continue;
                        }
                        if (activeCubes.contains(new Position(x + dx, y + dy, z + dz, w + dw))) {
                            count++;
                        }
                    }
                }
            }
        }
        return count;
    }
}
