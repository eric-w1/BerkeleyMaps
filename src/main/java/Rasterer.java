import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {

    double[] lonDPP;

    public Rasterer() {
        double lonDPP0 = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / MapServer.TILE_SIZE;
        double lonDPP1 = lonDPP0 / 2;
        double lonDPP2 = lonDPP0 / 4;
        double lonDPP3 = lonDPP0 / 8;
        double lonDPP4 = lonDPP0 / 16;
        double lonDPP5 = lonDPP0 / 32;
        double lonDPP6 = lonDPP0 / 64;
        double lonDPP7 = lonDPP0 / 128;
        lonDPP = new double[] {
            lonDPP0, lonDPP1, lonDPP2, lonDPP3, lonDPP4, lonDPP5, lonDPP6, lonDPP7
        };

    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        Map<String, Object> results = new HashMap<>();
        if (validateParams(params)) {
            mapOutputs(params, results);
            results.put("query_success", true);
        } else {
            results.put("query_success", false);
            results.put("raster_ul_lon", params.get("ullon"));
            results.put("raster_lr_lon", params.get("lrlon"));
            results.put("raster_ul_lat", params.get("ullat"));
            results.put("raster_lr_lat", params.get("lrlat"));
            results.put("render_grid", new String[][]{
                    {"d0_x0_y0.png", "d0_x0_y0.png", "d0_x0_y0.png"},
                    {"d0_x0_y0.png", "d0_x0_y0.png", "d0_x0_y0.png"},
                    {"d0_x0_y0.png", "d0_x0_y0.png", "d0_x0_y0.png"}});
            results.put("depth", -1);
        }
        return results;
    }

    private int bestDepth(double lrlon, double ullon, double w) {
        double qBoxDPP = (lrlon - ullon) / w;
        int bestDepth = 7;
        for (int d = 7; d >= 0; d -= 1) {
            if (lonDPP[d] < qBoxDPP) {
                bestDepth = d;
            }
        }
        return bestDepth;
    }


    private void mapOutputs(Map<String, Double> params, Map<String, Object> results) {
        double lrlon = params.get("lrlon");
        double ullon = params.get("ullon");
        double w = params.get("w");
        double h = params.get("h");
        double ullat = params.get("ullat");
        double lrlat = params.get("lrlat");
        int depth = bestDepth(lrlon, ullon, w);

        int[] imgBoundingBox = imgBoxDims(lrlon, ullon, ullat, lrlat, depth);
        int xleft = imgBoundingBox[0];
        int xright = imgBoundingBox[1];
        int ytop = imgBoundingBox[2];
        int ybottom = imgBoundingBox[3];

        String[][] images = getBoxImages(xleft, xright, ytop, ybottom, depth);
        double rasterUlLon = MapServer.ROOT_ULLON + xleft / Math.pow(2, depth)
                * (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON);
        double rasterLrLon = MapServer.ROOT_ULLON + (xright + 1) / Math.pow(2, depth)
                * (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON);
        double rasterUlLat = MapServer.ROOT_ULLAT - ytop / Math.pow(2, depth)
                * (MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT);
        double rasterLrLat = MapServer.ROOT_ULLAT - (ybottom + 1) / Math.pow(2, depth)
                * (MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT);
        results.put("raster_ul_lon", rasterUlLon);
        results.put("raster_lr_lon", rasterLrLon);
        results.put("raster_ul_lat", rasterUlLat);
        results.put("raster_lr_lat", rasterLrLat);
        results.put("render_grid", images);
        results.put("depth", depth);
    }

    private int[] imgBoxDims(double lrlon, double ullon, double ullat, double lrlat, int d) {
        double rootlrlon = MapServer.ROOT_LRLON;
        double rootullon = MapServer.ROOT_ULLON;
        double rootlrlat = MapServer.ROOT_LRLAT;
        double rootullat = MapServer.ROOT_ULLAT;
        int xleft;
        int xright;
        int ytop;
        int ybottom;

        if (lrlon >= rootlrlon) {
            xright = (int) Math.pow(2, d) - 1;
        } else {
            xright = (int) Math.pow(2, d) - (int) (Math.pow(2, d) *  Math.abs((rootlrlon - lrlon)
                    / (rootlrlon - rootullon))) - 1;
        }

        if (ullon <= rootullon) {
            xleft = 0;
        } else {
            xleft = (int) (Math.pow(2, d) *  Math.abs((ullon - rootullon)
                    / (rootlrlon - rootullon)));
        }

        if (lrlat <= rootlrlat) {
            ybottom = (int) Math.pow(2, d) - 1;
        } else {
            ybottom = (int) Math.pow(2, d) - (int) (Math.pow(2, d) *  Math.abs((lrlat - rootlrlat)
                    / (rootullat - rootlrlat))) - 1;
        }

        if (ullat >= rootullat) {
            ytop = 0;
        } else {
            ytop = (int) (Math.pow(2, d) *  Math.abs((rootullat - ullat)
                    / (rootullat - rootlrlat)));
        }

        return new int[] {xleft, xright, ytop, ybottom};
    }

    private String convertToFilename(int d, int x, int y) {
        return "d" + d + "_x" + x + "_y" + y + ".png";
    }

    private String[][] getBoxImages(int xleft, int xright, int ytop, int ybottom, int depth) {
        if (!(xright >= xleft && ybottom >= ytop)) {
            throw new RuntimeException("!(xright >= xleft && ybottom >= ytop)");
        }

        String[][] images = new String[ybottom - ytop + 1][xright - xleft + 1];

        int j = 0;
        for (int y = ytop; y <= ybottom; y += 1) {
            int i = 0;
            for (int x = xleft; x <= xright; x += 1) {
                images[j][i] = convertToFilename(depth, x, y);
                i += 1;
            }
            j += 1;
        }

        return images;
    }

    private boolean validateParams(Map<String, Double> params) {
        double lrlon = params.get("lrlon");
        double ullon = params.get("ullon");
        double ullat = params.get("ullat");
        double lrlat = params.get("lrlat");
        if (ullon > lrlon || ullat < lrlat) {
            return false;
        }
        if (ullon > MapServer.ROOT_LRLON || lrlon < MapServer.ROOT_ULLON) {
            return false;
        }
        if (ullat < MapServer.ROOT_LRLAT || lrlat > MapServer.ROOT_ULLAT) {
            return false;
        }
        return true;
    }
}
