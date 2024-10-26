import React, { useEffect, useState } from 'react';

const TaxCalculator = () => {
    const [taxData, setTaxData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchTaxData = async () => {
            try {
                const response = await fetch('http://localhost:8080/api/tax'); // Change this to your backend API endpoint
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                const data = await response.json();
                setTaxData(data);
            } catch (error) {
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

    const totalTax = taxData.totalTax || 0;
    const vehicleTaxes = taxData.vehicleTaxMap || {};

    return (
        <div>
            <h2>Total Tax Paid for Regular Vehicles: {totalTax} SEK</h2>
            <h3>Tax Paid by Registration Number:</h3>
            <ul>
                {Object.entries(vehicleTaxes).map(([registrationNumber, tax]) => (
                    <li key={registrationNumber}>
                        {registrationNumber}: {tax} SEK
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default TaxCalculator;
