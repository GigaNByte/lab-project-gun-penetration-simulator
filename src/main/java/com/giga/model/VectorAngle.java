package com.giga.model;

class VectorAngle {

    private static double magnitude(double arr[], int N) {

        double magnitude = 0;
        for (int i = 0; i < N; i++)
            magnitude += arr[i] * arr[i];
        return Math.sqrt(magnitude);
    }


    /**
     * @param arr first vector array
     * @param brr second vector array
     * @return Function to find the dot product of two vectors
     *
     */
    private static double dotProduct(double[] arr,
                                     double[] brr, int N) {

        double product = 0;
        for (int i = 0; i < N; i++)
            product = product + arr[i] * brr[i];
        return product;
    }

    /**
     * @param arr first vector array
     * @param brr second vector array
     * @return
     */
    public static Double angleBetweenVectors(double[] arr, double[] brr) {
        double dotProductOfVectors = dotProduct(arr, brr, arr.length);
        double magnitudeOfA = magnitude(arr, arr.length);
        double magnitudeOfB = magnitude(brr, brr.length);
        double angle = dotProductOfVectors /
                (magnitudeOfA * magnitudeOfB);

        return Math.toDegrees(Math.acos(angle));

    }

        // Given magnitude arrays

}