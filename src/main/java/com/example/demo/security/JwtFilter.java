@Override
protected void doFilterInternal(HttpServletRequest request,
                                HttpServletResponse response,
                                FilterChain filterChain) throws ServletException, IOException {

    String path = request.getServletPath();

    // Skip JWT validation for public endpoints
    if (path.startsWith("/auth") || path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs")) {
        filterChain.doFilter(request, response);
        return;
    }

    // Your existing JWT validation logic here
    String authHeader = request.getHeader("Authorization");
    // ...
}
