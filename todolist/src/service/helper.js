export const handleResponse = (response) => {
    return response.text().then((text) => {
        const data = text && JSON.parse(text);
        if (!response.ok) {
            const errorLog = {
                status: response.status,
                code: (data && data.ErrorCode) || '',
                msg: (data && data.ErrorMessage) || response.statusText,
            };
            return Promise.reject(errorLog);
        }
        return data;
    });
};