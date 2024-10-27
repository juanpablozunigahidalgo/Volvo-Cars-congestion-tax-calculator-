import React, { useEffect, useState } from 'react';

const TaxCalculator = () => {
    const [taxData, setTaxData] = useState({});
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchTaxData = async () => {
            try {
                const response = await fetch('/api/tax/calculate');
                
                const text = await response.text(); // Read response as text
                console.log('Response Text:', text);

                const contentType = response.headers.get('Content-Type');
                
                let data;
                if (contentType && contentType.includes('application/json')) {
                    // If response is JSON
                    data = JSON.parse(text);
                } else if (contentType && contentType.includes('text/html')) {
                    // If response is HTML, extract JSON from <pre> tag
                    const parser = new DOMParser();
                    const doc = parser.parseFromString(text, 'text/html');
                    const preElement = doc.querySelector('pre');
                    if (preElement) {
                        data = JSON.parse(preElement.innerText);
                    } else {
                        throw new Error('No <pre> element found in the response.');
                    }
                } else {
                    throw new Error(`Unexpected content type: ${contentType}`);
                }

                console.log('Fetched Tax Data:', data);
                setTaxData(data);

            } catch (error) {
                console.error('Error fetching tax data:', error);
                setError(error.message);
            } finally {
                setLoading(false);
            }
        };

        fetchTaxData();
    }, []);

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error: {error}</div>;
    }

    const totalTax = Object.values(taxData).reduce((acc, tax) => acc + tax, 0);

    return (
        <div>
            <h2>Total Tax Paid for Regular Vehicles: {totalTax} SEK</h2>
            <h3>Tax Paid by Registration Number:</h3>
            {Object.keys(taxData).length === 0 ? (
                <div>No tax data available.</div>
            ) : (
                <ul>
                    {Object.entries(taxData).map(([registrationNumber, tax]) => (
                        <li key={registrationNumber}>
                            {registrationNumber}: {tax} SEK
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default TaxCalculator;
