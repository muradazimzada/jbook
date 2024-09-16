import '@/app/styles/globals.css';
import Header from '@/app/components/Header';

export const metadata = {
    title: 'JBook App',
    description: 'Your gateway to managing and discovering books effortlessly.',
};

export default function RootLayout({ children }) {
    return (
        <html lang="en">
        <body>
        <Header />
        {children}
        </body>
        </html>

    );
}