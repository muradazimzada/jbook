// middleware.ts
import { NextResponse } from 'next/server';
import type { NextRequest } from 'next/server';

export function middleware(request: NextRequest) {
    const token = request.cookies.get('token')?.value;

    // Allow access to login and register pages even without a token
    if (request.nextUrl.pathname === '/login' || request.nextUrl.pathname === '/register') {
        return NextResponse.next();
    }

    // Redirect to login if no token is found and the user is trying to access a protected route
    if (!token) {
        return NextResponse.redirect(new URL('/login', request.url)); // Redirect to login if no token
    }

    // Allow access if the token is present
    return NextResponse.next(); // Proceed to the requested page if authenticated
}

// Apply middleware to all routes except login and register
export const config = {
    matcher: ['/((?!login|register).*)'], // Match all routes except /login and /register
};
