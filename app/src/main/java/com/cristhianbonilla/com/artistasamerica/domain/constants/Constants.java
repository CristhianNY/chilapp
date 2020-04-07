package com.cristhianbonilla.com.artistasamerica.domain.constants;

public interface Constants {

    // TODO Change it to your web domain
    public final static String WEB_DOMAIN = "zoom.us";

    // TODO Change it to your APP Key
    public final static String SDK_KEY = "ElJ6258n0fFDkqxIWnngSUzmxpSV3v8XqW09";

    // TODO Change it to your APP Secret
    public final static String SDK_SECRET = "EvRlas2m9DbQLpjc7ZTpCGYPRXnvYWiq9geA";

    /**
     * We recommend that, you can generate jwttoken on your own server instead of hardcore in the code.
     * We hardcore it here, just to run the demo.
     *
     * You can generate a jwttoken on the https://jwt.io/
     * with this payload:
     * {
     *     "appKey": "string", // app key
     *     "iat": long, // access token issue timestamp
     *     "exp": long, // access token expire time
     *     "tokenExp": long // token expire time
     * }
     */
    public final static String SDK_JWTTOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBLZXkiOiJmRkRrcXhJV25uZ1NVem14cFNWM3Y4WHFXMDkiLCJpYXQiOjE0OTYwOTE5NjQwMDAsImV4cCI6MTQ5NjA5MTk2NDAwMCwidG9rZW5FeHAiOjE0OTYwOTE5NjQwMDB9.mfPk04O9vt2ccozCVFkRiJmEOaI9hcp27JwQfWNtu8c";

}
