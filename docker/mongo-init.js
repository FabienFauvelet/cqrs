db.auth('root','root');

db.getName();

db.createUser(
        {
                user: 'albert',
                pwd: 'einstein',
                roles: [
                        { role: 'readWrite', db: 'courses'},
                        { role: 'readWrite', db: 'references'},
                        { role: 'readWrite', db: 'resources'},
                        { role: 'readWrite', db: 'teachers'}
                        ]
        }
);

db.createUser(
{
    user:'front',
    pwd:'front',
    roles:
        [
        {role: 'read', db: 'courses'},
        {role: 'read', db: 'references'},
        {role: 'read', db: 'teachers'}
        ]
});