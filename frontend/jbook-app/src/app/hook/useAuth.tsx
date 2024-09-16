import { useRouter } from 'next/navigation';
import { useEffect, useState } from 'react';
import cookieUtil from "@/app/util/cookieUtil";

export const useAuth = () => {
  const router = useRouter();
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  useEffect(() => {
    // Inspect if the user is logged in
    const token = cookieUtil.getTokenFromCookies();

    if (token) {
      // If token exists, set authentication state and redirect to home
      setIsAuthenticated(true);
      router.push('/');
    }
  }, [router]);

  return { isAuthenticated };
};
